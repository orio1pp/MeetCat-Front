package com.pes.meetcatui.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ChatApi {
    @GET("chats/{chatId}")
    suspend fun getChatData(@Path("chatId") chatId: Int): Response<EventDetailsData>
}