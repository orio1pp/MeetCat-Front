package com.pes.meetcatui.feature_chat.presentation.screen_chat

import com.pes.meetcatui.feature_chat.domain.Chat
import com.pes.meetcatui.feature_event.domain.Event

data class ChatScreenState(
    val data: Chat? = null,
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val errorMessage: String? = null
)
