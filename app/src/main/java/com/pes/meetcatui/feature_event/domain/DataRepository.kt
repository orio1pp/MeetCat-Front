package com.pes.meetcatui.feature_event.domain

import com.pes.meetcatui.common.Resource
import com.pes.meetcatui.feature_event.domain.green_wheel_api.Bike
import com.pes.meetcatui.feature_event.domain.green_wheel_api.Charger
import com.pes.meetcatui.network.UserData
import kotlinx.coroutines.flow.Flow

interface DataRepository {
    //fun getEvent(eventId: Int): Flow<Resource<Event>>
    fun getEvents(pageNum:Int, title:String?): Flow<Resource<EventPage>>
    fun getAllEvents(): Flow<Resource<EventPage>>
    fun getOwnEvents(): Flow<Resource<EventPage>>
    fun getAttendedEvents(): Flow<Resource<EventPage>>
    fun getReportedEvents(pageNum: Int, title:String?) : Flow<Resource<EventPage>>
    suspend fun createEvent(event:Event): String
    suspend fun updateEvent(event:Event): String
    suspend fun reportEvent(event: Event): String
    suspend fun unreportEvent(event: Event): String
    fun getNearestEvents(latitude: Double,longitude: Double,distance: Double): Flow<Resource<EventPage>>
    fun getAttendance(eventId: Long): Flow<Resource<Boolean>>
    suspend fun createAttendance(eventId: Long): Flow<Resource<Long>>
    suspend fun deleteAttendance(eventId: Long): Flow<Resource<Long>>
    suspend fun deleteEvent(eventId: Long): Flow<Resource<Unit>>
    suspend fun getUsername(): Flow<Resource<String>>
    suspend fun likeEvent(eventId: Long): String
    suspend fun dislikeEvent(eventId: Long): String
    fun getLiked(eventId: Long): Flow<Resource<Boolean>>
    fun getDisliked(eventId: Long): Flow<Resource<Boolean>>
    fun getNearestChargers(latitude: Double, longitude: Double, distance: Double): Flow<Resource<List<Charger>>>
    fun getNearestBikes(latitude: Double, longitude: Double, distance: Double): Flow<Resource<List<Bike>>>
    fun getAdminStatus(): Flow<Resource<Boolean>>


}
    //suspend fun downloadData()


