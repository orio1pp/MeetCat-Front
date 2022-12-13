package com.pes.meetcatui.feature_chat.domain

import com.pes.meetcatui.network.chat.GetChatData
import com.pes.meetcatui.network.chat.MessageData

interface DataRepositoryChats {
    suspend fun getChatByUser(page: Int): List<GetChatData>?
    suspend fun getMessagesByChat(chatId : Long, page: Int): List<MessageData>?
    suspend fun newMessage(messageData: MessageData): Boolean
    suspend fun getUsername(): String
}