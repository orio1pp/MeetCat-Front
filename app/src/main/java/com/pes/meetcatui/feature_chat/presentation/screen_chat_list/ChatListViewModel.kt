package com.pes.meetcatui.feature_chat.presentation.screen_chat_list

import androidx.lifecycle.ViewModel
import com.pes.meetcatui.feature_chat.domain.DataRepositoryChats

class ChatListViewModel(dataRepository: DataRepositoryChats) : ViewModel(

) {
    //val _chat = mutableStateOf(ChatScreenState())
/*
    val chatList = dataRepository.getChatList().mapLatest { chats ->
        chats.asSequence().sortedBy { it.chatId }.toList()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())*/

    fun getChat(id: Int) {
        /*_chat.value = ChatScreenState(
            data = chatList.value.get(id - 1),
        )*/
        println("break")
    }
}