package com.pes.meetcatui.feature_chat.domain

import com.pes.meetcatui.feature_event.Resource
import com.pes.meetcatui.feature_event.domain.Event
import kotlinx.coroutines.flow.Flow

interface DataRepository {
    fun getChat(chatId: Int): Flow<Resource<Message>>
    suspend fun downloadData()
}