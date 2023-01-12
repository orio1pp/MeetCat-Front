package com.pes.meetcatui.network.chat

import kotlinx.serialization.Serializable

@Serializable
data class GetChatData(
    val chatId: Long?,
    val friend: String?,
    val friendship: Long?
)
