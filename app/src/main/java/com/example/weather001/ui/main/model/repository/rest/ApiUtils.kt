package com.example.weather001.ui.main.model.repository.rest

import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient

object ApiUtils {
    private val baseUrlMainPart = "https://api.weather.yandex.ru/"
    private val baseUrlVersion = "v2/"
    val baseUrl = "$baseUrlMainPart$baseUrlVersion"

    fun getOkHTTPBuilderWithHeaders(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(10, TimeUnit.SECONDS)
        httpClient.readTimeout(10, TimeUnit.SECONDS)
        httpClient.writeTimeout(10, TimeUnit.SECONDS)
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("X-Yandex-API-Key", "3589d9e5-bb67-4397-8574-d8e99cc2fac3")
                .method(original.method(), original.body())
                .build()

            chain.proceed(request)
        }
        return httpClient.build()

    }
}