package com.pes.meetcatui.feature_event.domain

import com.pes.meetcatui.feature_event.Resource
import kotlinx.coroutines.flow.Flow

interface DataRepository {
    //fun getEvent(eventId: Int): Flow<Resource<Event>>
    fun getEvents(): Flow<Resource<List<Event>>>
    suspend fun createEvent(event:Event): String
    //suspend fun downloadData()
}

