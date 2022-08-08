package com.achmad.baseandroid.di

import com.achmad.baseandroid.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
            .header("Authorization", "token ${BuildConfig.TOKEN}")
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
