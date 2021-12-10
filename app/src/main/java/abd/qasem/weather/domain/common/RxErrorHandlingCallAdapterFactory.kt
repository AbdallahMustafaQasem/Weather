package abd.qasem.weather.domain.common

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.*
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory

import java.io.IOException
import java.lang.reflect.Type
import java.util.concurrent.TimeoutException


class RxErrorHandlingCallAdapterFactory private constructor() : CallAdapter.Factory() {

    private val _original by lazy {
        RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io())
    }

    companion object {
        fun create(): CallAdapter.Factory {
            return RxErrorHandlingCallAdapterFactory()
        }
    }

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit,
    ): CallAdapter<*, *> {
        val wrapped = _original.get(returnType, annotations, retrofit) as CallAdapter<out Any, *>
        return RxCallAdapterWrapper(retrofit, wrapped)
    }

    private class RxCallAdapterWrapper<R>(
        private val retrofit: Retrofit,
        private val wrapped: CallAdapter<R, *>,
    ) :
        CallAdapter<R, Any> {

        override fun responseType(): Type = wrapped.responseType()


        override fun adapt(call: Call<R>): Any {
            val result = wrapped.adapt(call)

            if (result is Flowable<*>)
                return result.onErrorResumeNext { throwable: Throwable ->
                    Flowable.error(asRetrofitException(throwable))
                }

            if (result is Single<*>)
                return result.onErrorResumeNext { throwable: Throwable ->
                    Single.error(asRetrofitException(throwable))
                }

            if (result is Observable<*>)
                return result.onErrorResumeNext { throwable: Throwable ->
                    Observable.error(asRetrofitException(throwable))
                }

            return if (result is Completable)
                result.onErrorResumeNext { throwable: Throwable ->
                    Completable.error(asRetrofitException(throwable))
                }
            else result
        }

        private fun asRetrofitException(throwable: Throwable): ErrorEntity {

            if (throwable is NoConnectivityException)
                return ErrorEntity.NetworkError(throwable)

            // We had non-200 http error
            if (throwable is HttpException) {
                val response = throwable.response()
                val error = getErrorBodyAs(response, retrofit, ServerError::class.java)
                val message = response!!.code().toString() + " " + response.message()
                return ErrorEntity.HttpError(
                    message,
                    throwable,
                    response.raw().request().url().toString(),
                    response,
                    retrofit,
                    error,
                    if (error == null) ResponseState.GeneralError else getResponseState(
                        response.code(),
                        error
                    )
                )
            }

            // A network error happened
            if (throwable is IOException) {
                return ErrorEntity.NetworkError(throwable)
            }
            return if (throwable is TimeoutException) {
                ErrorEntity.TimeoutError(throwable as IOException)
            }
            // We don't know what happened. We need to simply convert to an unknown error
            else ErrorEntity.UnexpectedError(throwable)

        }

        /**
         * HTTP response body converted to specified `type`. `null` if there is no
         * response.
         * @throws IOException if unable to convert the body to the specified `type`.
         */
        @Throws(IOException::class)
        fun <T> getErrorBodyAs(_response: Response<*>?, _retrofit: Retrofit, type: Class<T>): T? {

            if (_response?.errorBody() != null) {
                val converter: Converter<ResponseBody, T> =
                    _retrofit.responseBodyConverter(type, arrayOfNulls<Annotation>(0))
                return converter.convert(_response.errorBody()!!)
            }

            return null

        }

        fun getResponseState(errorCode: Int, serverError: ServerError): ResponseState {

            return try {

                when (errorCode) {
                    400 -> ResponseState.BadRequest(BadRequestState.values()[serverError.errorCode])
                    401 -> ResponseState.Unauthorized(UnauthorizedState.values()[serverError.errorCode])
                    403 -> ResponseState.Forbidden(ForbiddenState.values()[serverError.errorCode])
                    404 -> ResponseState.NotFound(NotFoundState.values()[serverError.errorCode])
                    500 -> ResponseState.ServiceError(ServiceErrorState.values()[serverError.errorCode])
                    502 -> ResponseState.NetworkError
                    503 -> ResponseState.ServiceUnavailable
                    else -> ResponseState.GeneralError
                }

            } catch (ex: Exception) {

                //TODO log the error

                ResponseState.GeneralError
            }
        }
    }


}
