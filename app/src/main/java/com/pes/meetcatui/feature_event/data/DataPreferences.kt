package com.pes.meetcatui.feature_event.data

import com.pes.meetcatui.feature_event.domain.Event
import kotlinx.coroutines.flow.Flow

interface DataPreferences {
    fun getEventList(): Flow<List<Event>>
    //fun getEvent(id: Number): Flow<Event>
    suspend fun setEventList(eventList: List<Event>)
}

const val DATA_PREFERENCES_NAME = "DataPreferences"
