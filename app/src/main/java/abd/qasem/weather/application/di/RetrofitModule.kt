package abd.qasem.weather.application.di

import abd.qasem.weather.application.common.NetworkConnectionInterceptor
import abd.qasem.weather.application.common.RequestInterceptor
import abd.qasem.weather.domain.common.RxErrorHandlingCallAdapterFactory
import android.content.Context
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor


import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

import dagger.Module

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://wac.uhive.com/api/v1/")
        .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()


    @Singleton
    @Provides
    fun provideRetrofitClint(@ApplicationContext context: Context): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(OkHttpProfilerInterceptor())
            .addInterceptor(RequestInterceptor())
            .addInterceptor(NetworkConnectionInterceptor(context))
            .build()


}