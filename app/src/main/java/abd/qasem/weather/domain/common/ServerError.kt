package abd.qasem.weather.domain.common

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ServerError(
    @SerializedName("error_code")
    @Expose
    val errorCode: Int,
    @SerializedName("error_message")
    @Expose
    val errorMessage: String?,
)

