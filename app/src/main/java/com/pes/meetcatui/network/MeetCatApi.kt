package com.pes.meetcatui.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MeetCatApi {
    @GET("events/{eventId}")
    suspend fun getEventData(@Path("eventId") eventId: Int): Response<EventDetailsData>

    @GET("events")
    suspend fun getEvents(@Query("page") page: Int?, @Query("size") size:Int?): Response<EventsData>

    @POST("events")
    suspend fun createEvent(@Body event: EventDetailsData)
    
}
