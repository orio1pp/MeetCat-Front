package com.pes.meetcatui.feature_chat.domain

import com.pes.meetcatui.feature_event.Resource
import com.pes.meetcatui.network.chat.ChatData
import com.pes.meetcatui.network.chat.GetChatData
import kotlinx.coroutines.flow.Flow

interface DataRepositoryChats {
    fun getChatByUser(): Flow<GetChatData>
}