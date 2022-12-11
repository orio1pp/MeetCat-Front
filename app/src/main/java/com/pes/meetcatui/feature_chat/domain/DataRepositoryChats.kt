package com.pes.meetcatui.feature_chat.domain

import com.pes.meetcatui.network.chat.GetChatData

interface DataRepositoryChats {
    suspend fun getChatByUser(page: Int): List<GetChatData>?
}