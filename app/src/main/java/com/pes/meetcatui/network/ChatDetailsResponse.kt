package com.pes.meetcatui.network

import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.SerialName

data class ChatDetailsResponse(
    @SerialName("id")
    val chatId: Int,
    @SerialName("friendshipId")
    val friendshipId: Int,
    @SerialName("username")
    val username: Int,
    @SerialName("messages")
    val subtitle: Flow<String>,
)
