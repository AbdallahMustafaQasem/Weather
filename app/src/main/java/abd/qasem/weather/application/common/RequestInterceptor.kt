package abd.qasem.weather.application.common


import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor constructor() : Interceptor {


    companion object {
        const val BUILD_NUMBER_HEADER_KEY = "BUILDNO"
        const val SKIP_AUTHORIZATION_HEADER = "Skip_Authorization"
        const val AUTHORIZATION_HEADER_STARTED_VALUE = "Bearer "
        const val AUTHORIZATION_HEADER_KEY = "Authorization"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val newRequest = chain.request().newBuilder()

        // newRequest.addHeader(BUILD_NUMBER_HEADER_KEY, deviceWithBuildNumber())

        if (original.header(SKIP_AUTHORIZATION_HEADER).isNullOrBlank() ||
            !original.header(SKIP_AUTHORIZATION_HEADER).isNullOrBlank() && original.header(
                SKIP_AUTHORIZATION_HEADER
            )!!.lowercase() == "false"
        ) {
            /*   newRequest.addHeader(AUTHORIZATION_HEADER_KEY,
                   AUTHORIZATION_HEADER_STARTED_VALUE + getAccessToken())*/
        }

        return chain.proceed(newRequest.build())
    }


}