package com.pes.meetcatui.network

import com.pes.meetcatui.data.DataPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class MeetCatApiInterceptor(
    val dataPreferences: DataPreferences,
    val appScope: CoroutineScope
) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain
            .request()
            .newBuilder()
            .build()
        appScope.launch {
            var access_token: String =
                "Bearer " + dataPreferences.getAccessToken()

            request = chain
                .request()
                .newBuilder()
                .addHeader("Authorization", access_token)
                .build()
        }
        println("LLLLLLLLLLLLLLLLLL" + request.headers)
        return chain.proceed(request)
    }
}
