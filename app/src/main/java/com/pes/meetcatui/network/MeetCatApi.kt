package com.pes.meetcatui.network

import com.pes.meetcatui.feature_user.domain.UserToken
import com.pes.meetcatui.network.Friendships.FriendshipData
import com.pes.meetcatui.network.Friendships.GetFriendshipsData
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.*

interface MeetCatApi {
    @GET("events/{eventId}")
    suspend fun getEventData(@Path("eventId") eventId: Int): Response<EventDetailsData>

    @GET("events")
    suspend fun getEvents(@Query("page") page: Int?, @Query("size") size:Int?): Response<EventsData>

    @GET("events")
    suspend fun getEventsWithTitle(@Query("page") page: Int?, @Query("size") size:Int?, @Query("title") title:String?): Response<EventsData>

    @POST("events")
    suspend fun createEvent(@Body event: EventDetailsData)

    @POST("users")
    suspend fun postUser(user : UserData): Response<UserData>

    @POST("login")
    suspend fun login(@Query("username") username : String, @Query("password") password : String): Response<UserToken>

    @GET("users/name")
    suspend fun getUser(@Query("username") username : String, @Header("Authorization") accessToken : String): Response<UserData>

    @POST("friendship")
    suspend fun addFriend(@Body friendship: FriendshipData, @Header("Authorization") accessToken : String): Response<FriendshipData>

    @GET("friendship")
    suspend fun getFriend(@Query("page") page: Int?, @Query("size") size: Int?, @Header("Authorization") accessToken : String): Response<List<FriendshipData>>?

    @DELETE("friendship")
    suspend fun removeFriend(@Query("friendId") friendId: String, @Header("Authorization") accessToken : String): Response<FriendshipData>
}
