package com.pes.meetcatui.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MeetCatApi {
    @GET("{eventId}")
    suspend fun getEventData(@Path("eventId") eventId: Int): Response<EventDetailsData>

    @GET()
    suspend fun getAllEvents(): Response<List<EventDetailsData>>
}