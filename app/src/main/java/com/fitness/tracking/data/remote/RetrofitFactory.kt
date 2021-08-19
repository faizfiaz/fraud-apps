package com.fitness.tracking.data.remote

import com.fitness.tracking.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitFactory private constructor() {
    val remoteAPI: RemoteAPI

    companion object {
        private const val READ_TIMEOUT_SECONDS = 60
        private const val CONNECT_TIMEOUT_SECONDS = 60
        var instance: RetrofitFactory? = null
            get() {
                if (field == null) {
                    field = RetrofitFactory()
                }
                return field
            }
            private set
    }

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val clientBuilder = OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT_SECONDS.toLong(), TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT_SECONDS.toLong(), TimeUnit.SECONDS)
                .addInterceptor(interceptor)

        if (BuildConfig.USE_MOCK_RESPONSE) {
            clientBuilder.addInterceptor(MockInterceptor())
        }
        clientBuilder.addInterceptor(fun(chain: Interceptor.Chain): Response? {
            val request: Request = chain.request().newBuilder()
                    .addHeader("secretkey", "4df4afd62a9d60b5681e61c8db1dba5b")
                    .addHeader("appid", "105")
                    .build()
            return chain.proceed(request)
        })
        val httpClient = clientBuilder.build()
        val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()
        remoteAPI = retrofit.create(RemoteAPI::class.java)
    }
}