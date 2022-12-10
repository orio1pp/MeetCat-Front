package com.pes.meetcatui.feature_event.domain

import com.pes.meetcatui.feature_event.Resource
import kotlinx.coroutines.flow.Flow

interface DataRepository {
    //fun getEvent(eventId: Int): Flow<Resource<Event>>
    fun getEvents(pageNum:Int): Flow<Resource<EventPage>>
    fun getAllEvents(): Flow<Resource<EventPage>>
    suspend fun likeEvent(eventId: Long)
    suspend fun dislikeEvent(eventId: Long)
    suspend fun createEvent(event:Event): String
    //suspend fun downloadData()
}

