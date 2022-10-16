package com.pes.meetcatui.domain

import kotlinx.coroutines.flow.Flow

interface DataRepository {
    fun getEvent(eventId: Int): Flow<Event>
}

