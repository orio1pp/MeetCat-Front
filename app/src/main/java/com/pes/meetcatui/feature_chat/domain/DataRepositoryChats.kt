package com.pes.meetcatui.feature_chat.domain

import com.pes.meetcatui.network.Friendships.FriendshipData
import com.pes.meetcatui.network.chat.ChatData
import com.pes.meetcatui.network.chat.ChatFriendshipData
import com.pes.meetcatui.network.chat.GetChatData
import com.pes.meetcatui.network.chat.MessageData

interface DataRepositoryChats {
    suspend fun getChatByUser(page: Int): List<GetChatData>?
    suspend fun getMessagesByChat(chatId : Long, page: Int): List<MessageData>?
    suspend fun newMessage(messageData: MessageData): Boolean
    suspend fun getUsername(): String
    suspend fun getFriend(): List<FriendshipData>?
    suspend fun getChatByFriendship(id : Long): ChatFriendshipData?
    suspend fun newChat(id : Long)

}