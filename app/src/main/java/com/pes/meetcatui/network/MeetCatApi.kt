package com.pes.meetcatui.network

import com.pes.meetcatui.feature_user.domain.UserToken
import retrofit2.Response
import retrofit2.http.*

interface MeetCatApi {
    @GET("events/{eventId}")
    suspend fun getEventData(@Path("eventId") eventId: Int): Response<EventDetailsData>

    @GET("events")
    suspend fun getEvents(@Query("page") page: Int?, @Query("size") size:Int?): Response<EventsData>

    @POST("users")
    suspend fun postUser(user : UserData): Response<UserData>

    @POST("login")
    suspend fun login(@Query("username") username : String, @Query("password") password : String): Response<UserToken>
}