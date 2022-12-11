package com.pes.meetcatui.feature_chat.presentation.screen_chat_list

import com.pes.meetcatui.feature_chat.domain.Chat
import com.pes.meetcatui.network.chat.GetChatData

data class ChatListScreenState(
    var data: MutableList<GetChatData>? = null,
    val hasError: Boolean = false,
    val errorMessage: String? = null,
    val isChatSelected: Boolean = false,
    val chatSelected: Chat? = null,
    val page: Int = 0,
)
