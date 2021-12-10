package abd.qasem.weather.domain.common


import retrofit2.Response
import retrofit2.Retrofit
import java.io.Serializable

sealed class ErrorEntity(
    message: String? = null,
    cause: Throwable? = null,
    val url: String? = null,
    val response: Response<*>? = null,
    val retrofit: Retrofit? = null,
    val error: ServerError? = null,
    val responseState: ResponseState,
) : RuntimeException(message, cause), Serializable {

    class NetworkError(throwable: Throwable) :
        ErrorEntity(
            message = throwable.message,
            cause = throwable,
            responseState = ResponseState.NetworkError
        )

    class UnexpectedError(throwable: Throwable) :
        ErrorEntity(
            message = throwable.message,
            cause = throwable,
            responseState = ResponseState.GeneralError
        )

    class TimeoutError(throwable: Throwable) :
        ErrorEntity(
            message = throwable.message,
            cause = throwable,
            responseState = ResponseState.CannotConnectToServiceError
        )

    class HttpError(
        message: String,
        cause: Throwable,
        url: String?,
        response: Response<*>?,
        retrofit: Retrofit?,
        error: ServerError?,
        state: ResponseState,
    ) : ErrorEntity(message, cause, url, response, retrofit, error, state)
}