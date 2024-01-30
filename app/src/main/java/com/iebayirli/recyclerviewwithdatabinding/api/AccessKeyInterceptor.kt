package com.iebayirli.recyclerviewwithdatabinding.api

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Request
import okhttp3.Response

class AccessKeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Chain): Response {
        val originalRequest: Request = chain.request()
        val originalHttpUrl: HttpUrl = originalRequest.url
        val newHttpUrl = originalHttpUrl.newBuilder()
            .addQueryParameter("access_key", apiKey)
            .build()
        val newRequest: Request = originalRequest.newBuilder()
            .url(newHttpUrl)
            .build()
        return chain.proceed(newRequest)
    }
}
