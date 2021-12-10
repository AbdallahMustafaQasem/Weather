package abd.qasem.weather.application.common

import abd.qasem.weather.domain.common.NoConnectivityException
import abd.qasem.weather.presentation.common.extensions.isNetworkAvailable
import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(val mContext: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isNetworkConnected()) {
            throw NoConnectivityException()
        }
        val builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }


    private fun isNetworkConnected(): Boolean {
        return mContext.isNetworkAvailable()
    }

}