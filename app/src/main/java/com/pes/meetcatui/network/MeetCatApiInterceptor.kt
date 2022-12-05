package com.pes.meetcatui.network

import com.pes.meetcatui.feature_user.data.DataPreferences
import okhttp3.Interceptor
import okhttp3.Response


class MeetCatApiInterceptor(val dataPreferences: DataPreferences) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain
            .request()
            .newBuilder()
            //.addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer ")
            .build()
        return chain.proceed(request)
    }
}