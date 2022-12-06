package com.pes.meetcatui.network

import android.content.Context
import com.pes.meetcatui.di.dataModule
import com.pes.meetcatui.feature_user.data.DataPreferences
import okhttp3.Interceptor
import okhttp3.Response


class MeetCatApiInterceptor(val dataPreferences: DataPreferences) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var access_token : String = "Bearer " + dataPreferences.getAccessToken()
        val request = chain
            .request()
            .newBuilder()
            .addHeader("Authorization", access_token)
            .build()

        return chain.proceed(request)
    }
}