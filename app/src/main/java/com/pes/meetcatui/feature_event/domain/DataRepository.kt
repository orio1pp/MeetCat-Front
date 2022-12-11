package com.pes.meetcatui.feature_event.domain

import com.pes.meetcatui.common.Resource
import kotlinx.coroutines.flow.Flow

interface DataRepository {
    //fun getEvent(eventId: Int): Flow<Resource<Event>>
    fun getEvents(pageNum:Int, title:String?): Flow<Resource<EventPage>>
    fun getAllEvents(): Flow<Resource<EventPage>>
    suspend fun createEvent(event:Event): String
    fun getNearestEvents(latitude: Double,longitude: Double,distance: Double): Flow<Resource<EventPage>>
    fun getAttendance(eventId: Long): Flow<Resource<Boolean>>
    suspend fun createAttendance(eventId: Long): Flow<Resource<Long>>
    suspend fun deleteAttendance(eventId: Long): Flow<Resource<Long>>
    suspend fun getUser(): Flow<String>
    //suspend fun downloadData()
}

