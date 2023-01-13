package com.pes.meetcatui.network.chat

import com.pes.meetcatui.network.Friendships.FriendshipData
import kotlinx.serialization.Serializable

@Serializable
data class ChatFriendshipData(
    val chatId: Long? = null,
    val friendship: FriendshipData? = null,
    val messageList: List<MessageData>? = null,
)
