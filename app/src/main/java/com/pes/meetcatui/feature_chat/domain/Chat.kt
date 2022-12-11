package com.pes.meetcatui.feature_chat.domain

import com.pes.meetcatui.network.chat.MessageData

data class Chat(
    var chatId: Long,
    var friend: String,
    var messageList: List<MessageData>
)
