package com.pes.meetcatui.feature_event.domain

import com.pes.meetcatui.Resource
import kotlinx.coroutines.flow.Flow

interface DataRepository {
    fun getEvent(eventId: Int): Flow<Resource<Event>>
}

