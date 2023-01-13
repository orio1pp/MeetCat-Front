package com.pes.meetcatui.network.chat

import kotlinx.serialization.Serializable

@Serializable
data class ChatData(
    var chatId: Long,
    var friendship: Long,
    var messageList: List<Long>?
)
