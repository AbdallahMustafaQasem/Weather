package abd.qasem.weather.domain.common

import androidx.annotation.Nullable

sealed class Resource<T>(
    @Nullable
    val data: T? = null,
    @Nullable
    val message: String? = null,
    @Nullable
    val exception: ErrorEntity? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(@Nullable data: T? = null) : Resource<T>(data)
    class Error<T>(
        @Nullable message: String? = null,
        @Nullable data: T? = null,
        exception: ErrorEntity
    ) :
        Resource<T>(data, message, exception)
}