package com.pes.meetcatui.feature_chat.domain

data class Chat(
    val chatId: Int,
    val username: String,
    val messages: List<Message>,
)
