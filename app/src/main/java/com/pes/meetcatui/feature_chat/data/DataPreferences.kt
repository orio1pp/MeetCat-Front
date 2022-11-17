package com.pes.meetcatui.feature_chat.data

import com.pes.meetcatui.feature_event.domain.Event
import kotlinx.coroutines.flow.Flow

interface DataPreferences {
    fun getChatList(): Flow<List<Event>>
    //fun getEvent(id: Number): Flow<Event>
    suspend fun setChatList(chatList: List<Event>)
}

const val CHAT_PREFERENCES_NAME = "ChatPreferences"