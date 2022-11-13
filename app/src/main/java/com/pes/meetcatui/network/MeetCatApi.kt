package com.pes.meetcatui.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MeetCatApi {
    @GET("events/{eventId}")
    suspend fun getEventData(@Path("eventId") eventId: Int): Response<EventDetailsData>

    @GET("events")
    suspend fun getEvents(): Response<List<EventDetailsData>>
}