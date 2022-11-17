package com.pes.meetcatui.feature_chat.data

import com.pes.meetcatui.feature_chat.domain.Chat
import com.pes.meetcatui.feature_event.domain.Event
import kotlinx.coroutines.flow.Flow

interface DataPreferences {
    fun getChatList(): Flow<List<Chat>>
    //fun getChat(id: Number): Flow<Chat>
    suspend fun setChatList(chatList: List<Chat>)
}

const val DATA_PREFERENCES_NAME = "ChatPreferences"