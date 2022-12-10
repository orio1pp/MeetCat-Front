package com.pes.meetcatui.network.chat

import kotlinx.serialization.Serializable

@Serializable
data class MessageData(
    var messageId: Long?,
    var text: String?,
    var date: String?,
    var chatId: Long?,
    var username: String
)