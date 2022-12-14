package com.pes.meetcatui.feature_chat.presentation.screen_chat_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.pes.meetcatui.feature_chat.domain.Chat
import com.pes.meetcatui.network.chat.GetChatData

data class ChatListScreenState(
    var data: MutableList<GetChatData>? = null,
    val hasError: Boolean = false,
    val errorMessage: String? = null,
    val isChatSelected: Boolean = false,
    val chatSelected: MutableState<Chat?> = mutableStateOf(null),
    val page: Int = 0,
)
