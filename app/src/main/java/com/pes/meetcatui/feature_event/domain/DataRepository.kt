package com.pes.meetcatui.feature_event.domain

import kotlinx.coroutines.flow.Flow

interface DataRepository {
    fun getEvent(eventId: Int): Flow<Event>
}

