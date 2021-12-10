package abd.qasem.weather.domain.common

import java.lang.RuntimeException

open class RequestException(val throwable: Throwable) : RuntimeException()
data class NoRequestException(val error: Throwable) : RequestException(error)
data class MapGsonException(val error: Throwable) : RequestException(error)
data class TimeoutException(val error: Throwable) : RequestException(error)
data class ServerUnreachableException(val error: Throwable) : RequestException(error)
data class HttpCallFailureException(val error: Throwable) : RequestException(error)
data class ResponseFailureException(val error: Throwable) : RequestException(error)
data class SessionTimeOut(val error: Throwable) : RequestException(error)
data class NounException(val error: Throwable) : RequestException(error)
