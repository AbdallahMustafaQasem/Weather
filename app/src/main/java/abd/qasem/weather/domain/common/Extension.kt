package abd.qasem.weather.domain.common

import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


fun <R> mapObservableNetworkErrors() = { observable: Observable<R> ->
    observable
        .onErrorResumeNext { error: Throwable ->
            when (error) {
                is ConnectException -> {
                    Observable.error(
                        NoRequestException(
                            error
                        )
                    )
                }
                is NoConnectivityException -> Observable.error(
                    NoRequestException(
                        error
                    )
                )
                is SocketTimeoutException -> Observable.error(
                    TimeoutException(
                        error
                    )
                )
                is UnknownHostException -> Observable.error(
                    ServerUnreachableException(
                        error
                    )
                )
                is HttpException -> Observable.error(
                    HttpCallFailureException(
                        error
                    )
                )
                else -> Observable.error(error)
            }
        }
}


//IllegalStateException map error
fun <R> mapNetworkErrors() = { single: Single<R> ->
    single
        .onErrorResumeNext { error ->
            when (error) {
                is ConnectException -> {
                    Single.error(
                        NoRequestException(
                            error
                        )
                    )
                }
                is IllegalStateException -> {
                    Single.error(
                        MapGsonException(
                            error
                        )
                    )
                }
                is NoConnectivityException -> Single.error(
                    NoRequestException(
                        error
                    )
                )
                is SocketTimeoutException -> Single.error(
                    TimeoutException(
                        error
                    )
                )
                is UnknownHostException -> Single.error(
                    ServerUnreachableException(
                        error
                    )
                )
                is HttpException -> Single.error(
                    HttpCallFailureException(
                        error
                    )
                )
                else -> Single.error(error)
            }
        }
}

fun <R> mapFlowableNetworkErrors() = { flowable: Flowable<R> ->
    flowable
        .onErrorResumeNext { error: Throwable ->
            when (error) {
                is ConnectException -> {
                    Flowable.error(
                        NoRequestException(
                            error
                        )
                    )
                }
                is NoConnectivityException -> Flowable.error(
                    NoRequestException(
                        error
                    )
                )
                is SocketTimeoutException -> Flowable.error(
                    TimeoutException(
                        error
                    )
                )
                is UnknownHostException -> Flowable.error(
                    ServerUnreachableException(
                        error
                    )
                )
                is HttpException -> Flowable.error(
                    HttpCallFailureException(
                        error
                    )
                )
                else -> Flowable.error(error)
            }
        }
}





