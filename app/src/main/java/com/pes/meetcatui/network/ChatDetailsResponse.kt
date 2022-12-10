package com.pes.meetcatui.network

import com.pes.meetcatui.feature_chat.domain.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.SerialName

data class ChatDetailsResponse(
    @SerialName("id")
    val chatId: Int,
    /*@SerialName("friendshipId")
    val friendshipId: Int,*/
    @SerialName("username")
    val username: String,
    @SerialName("messages")
    val messages: List<Message>,
)
