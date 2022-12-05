package com.pes.meetcatui.feature_chat.presentation.screen_chat_list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pes.meetcatui.feature_chat.domain.DataRepository
import com.pes.meetcatui.feature_chat.presentation.screen_chat.ChatScreenState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn

class ChatListViewModel(dataRepository: DataRepository) : ViewModel(

) {
    val dataRepository = dataRepository
    val _chat = mutableStateOf(ChatScreenState())

    val chatList = dataRepository.getChatList().mapLatest { chats ->
        chats.asSequence().sortedBy { it.chatId }.toList()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun getChat(id: Int) {
        _chat.value = ChatScreenState(
            data = chatList.value.get(id - 1),
        )
        println("break")
    }
}