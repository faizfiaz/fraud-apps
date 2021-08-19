package com.fitness.tracking.data.remote

import com.fitness.tracking.App
import com.fitness.tracking.BuildConfig
import com.fitness.tracking.utils.AndroidUtils
import okhttp3.*
import java.io.IOException

class MockInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (BuildConfig.USE_MOCK_RESPONSE) {
            val uri = chain.request().url().uri().toString()
            val response = AndroidUtils.loadJSONFromAsset(App.appContext!!, MockUri.getUriByString(uri).filename)
            chain.proceed(chain.request())
                    .newBuilder()
                    .code(StatusCode.SUCCESS)
                    .protocol(Protocol.HTTP_2)
                    .message(response!!)
                    .body(ResponseBody.create(MediaType.parse("application/json"),
                            response.toByteArray()))
                    .addHeader("content-type", "application/json")
                    .build()
        } else {
            throw IllegalAccessError("MockInterceptor is only meant for Testing Purposes and " +
                    "bound to be used only with DEBUG mode")
        }
    }

    enum class MockUri(var uri: String, var filename: String) {
        LOGIN("/auth/local", "responses/login.json"), LOCATION_LIST("/location", "responses/mock_location_list.json"), UNKNOWN("", "");

        companion object {
            fun getUriByString(name: String): MockUri {
                for (ob in values()) {
                    if (name.endsWith(ob.uri)) {
                        return ob
                    }
                }
                return UNKNOWN
            }
        }

    }
}