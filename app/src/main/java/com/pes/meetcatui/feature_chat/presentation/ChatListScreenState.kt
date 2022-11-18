package com.pes.meetcatui.feature_chat.presentation

import com.pes.meetcatui.feature_chat.domain.Chat
import com.pes.meetcatui.feature_event.domain.Event

data class ChatListScreenState(
    val data: List<Chat>? = null,
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val errorMessage: String? = null
)