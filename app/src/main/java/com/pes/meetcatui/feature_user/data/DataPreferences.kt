package com.pes.meetcatui.feature_user.data

import com.pes.meetcatui.feature_event.domain.Event
import com.pes.meetcatui.feature_user.domain.UserToken
import kotlinx.coroutines.flow.Flow

interface DataPreferences {
    fun getEventList(): Flow<List<Event>>
    //fun getEvent(id: Number): Flow<Event>
    suspend fun setEventList(eventList: List<Event>)
    suspend fun setToken(token: UserToken)
    fun getAccessToken(): Flow<String>
    fun getRefreshToken(): Flow<String>
}

const val DATA_PREFERENCES_NAME = "DataPreferences"
