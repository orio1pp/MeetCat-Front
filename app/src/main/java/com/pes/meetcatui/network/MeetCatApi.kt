package com.pes.meetcatui.network

import com.pes.meetcatui.feature_chat.domain.Chat
import com.pes.meetcatui.feature_user.domain.UserToken
import com.pes.meetcatui.network.Friendships.FriendshipData
import com.pes.meetcatui.network.chat.ChatData
import com.pes.meetcatui.network.chat.ChatFriendshipData
import com.pes.meetcatui.network.chat.GetChatData
import com.pes.meetcatui.network.chat.MessageData
import com.pes.meetcatui.feature_event.domain.green_wheel_api.Bike
import com.pes.meetcatui.feature_event.domain.green_wheel_api.Charger
import com.pes.meetcatui.feature_user.domain.UserToken
import com.pes.meetcatui.network.Friendships.FriendshipData
import com.pes.meetcatui.network.green_wheel.BikeData
import com.pes.meetcatui.network.green_wheel.ChargerData
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

    @PUT("events/{eventId}/report")
    suspend fun reportEvent(@Path("eventId") eventId: Long)

    @DELETE("events/{eventId}")
    suspend fun deleteEvent(@Path("eventId") eventId: Long, @Header("Authorization") accessToken: String): Response<Unit>

    @GET("events")
    suspend fun getEvents(@Query("page") page: Int?, @Query("size") size:Int?): Response<EventsData>

    @GET("events")
    suspend fun getEventsWithTitle(@Query("page") page: Int?, @Query("size") size:Int?, @Query("title") title:String?): Response<EventsData>

    @GET("events/reported")
    suspend fun getReportedEventsWithTitle(@Query("page") page: Int?, @Query("size") size:Int?, @Query("title") title:String?): Response<EventsData>

    @GET("events")
    suspend fun getEventsByUser(@Query("page") page: Int?, @Query("size") size:Int?, @Query("username") username:String?): Response<EventsData>

    @POST("events")
    suspend fun createEvent(@Body event: EventDetailsData, @Header("Authorization") accessToken: String)

    @PUT("events/{eventId}")
    suspend fun updateEvent(@Path("eventId") eventId: Long, @Body event: EventDetailsData, @Header("Authorization") accessToken: String)

    @POST("users")
    suspend fun postUser(@Body user : UserData): Response<UserData>

    @POST("login")
    suspend fun login(@Query("username") username : String, @Query("password") password : String): Response<UserToken>

    @GET("attendance")
    suspend fun getAttendance(@Query("eventId") eventId: Long, @Header("Authorization") accessToken: String): Response<Boolean>

    @POST("attendance")
    suspend fun createAttendance(@Body attendance: AttendanceData, @Header("Authorization") accessToken: String): Response<AttendanceData>

    @DELETE("attendance")
    suspend fun deleteAttendance(@Query("eventId") eventId: Long, @Header("Authorization") accessToken: String): Response<AttendanceData>

    @GET("users/name")
    suspend fun getUser(@Query("username") username : String, @Header("Authorization") accessToken : String): Response<UserData>

    @GET("users/me")
    suspend fun getUserByAuth(@Header("Authorization") accessToken : String): Response<UserData>

    @POST("friendship")
    suspend fun addFriend(@Body friendship: FriendshipData, @Header("Authorization") accessToken : String): Response<FriendshipData>

    @GET("friendship")
    suspend fun getFriend(@Query("page") page: Int?, @Query("size") size: Int?, @Header("Authorization") accessToken : String): Response<List<FriendshipData>>?

    @DELETE("friendship")
    suspend fun removeFriend(@Query("friendId") friendId: String, @Header("Authorization") accessToken : String): Response<FriendshipData>

    @GET("chat/username")
    suspend fun getChatsByUser(@Header("Authorization") accessToken : String): Response<List<GetChatData>>

    @GET("chat")
    suspend fun getChatByFriendship(@Query("friendshipId") friendshipId: Long, @Header("Authorization") accessToken : String): Response<ChatFriendshipData>

    @GET("message")
    suspend fun getMessagesByChat(
        @Query("chatId") chatId: Long,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Header("Authorization") accessToken : String
    ): Response<List<MessageData>>

    @POST("message")
    suspend fun postMessage(
        @Body message: MessageData,
        @Header("Authorization") accessToken : String
    ): Response<List<GetChatData>>

    @POST("chat")
    suspend fun insertChat(
        @Body chat: ChatData,
        @Header("Authorization") accessToken : String
    ): Response<List<GetChatData>>
    suspend fun getUser(@Query("username") username : String): Response<UserData>

    @DELETE("users/{id}")
    suspend fun removeAccount(@Path("id") id: Long): Response<UserData>

    @GET("events/nearest")
    suspend fun getNearestEvents(@Query("latitude") latitude: Double, @Query("longitude") longitude:Double, @Query("distance")distance:Double): Response<EventsData>

    @PUT("events/{eventId}/like")
    suspend fun likeEvent(@Path("eventId") eventId: Long, @Query("username") username:String)
//could use username in savedpreference in body or smth
    @PUT("events/{eventId}/dislike")
    suspend fun dislikeEvent(@Path("eventId") eventId: Long,  @Query("username") username:String)

    @GET("events/{eventId}/liked")
    suspend fun getLiked(@Path("eventId") eventId: Long, @Query("username") username:String): Response<Boolean>

    @GET("events/{eventId}/disliked")
    suspend fun getDisliked(@Path("eventId") eventId: Long, @Query("username") username:String): Response<Boolean>

    @GET("greenwheel/chargers")
    suspend fun getNearestChargers(@Query("latitude") latitude: Double, @Query("longitude") longitude:Double, @Query("distance")distance:Double): Response<List<ChargerData>>

    @GET("greenwheel/bikes")
    suspend fun getNearestBikes(@Query("latitude") latitude: Double, @Query("longitude") longitude:Double, @Query("distance")distance:Double): Response<List<BikeData>>
}
