package com.pes.meetcatui.feature_chat.presentation.screen_chat_list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pes.meetcatui.feature_chat.domain.Chat
import com.pes.meetcatui.feature_chat.domain.DataRepositoryChats
import com.pes.meetcatui.feature_event.domain.Event
import com.pes.meetcatui.feature_event.presentation.EventListScreenState
import kotlinx.coroutines.launch

class ChatListViewModel(
    val dataRepository: DataRepositoryChats
) : ViewModel() {

    val chatList = mutableStateOf(ChatListScreenState())

    init {
        viewModelScope.launch {
            val allChats = dataRepository.getChatByUser(0)?.toMutableList()
            if (allChats != null) {
                chatList.value = ChatListScreenState(
                    data = allChats,
                    errorMessage = "Couldn't get chats"
                )
            }
            else {
                chatList.value = ChatListScreenState(
                    hasError = true,
                    page = 1
                )
            }
        }
    }

    fun setSelectedChat(chat: Chat) {
        chatList.value = ChatListScreenState(
            isChatSelected = true,
            chatSelected = chat,
            data = chatList.value.data
        )
    }

    fun setIsSelected() {
        /*eventList.value = EventListScreenState(
            isDetailsSelected = false,
            data = eventList.value.data
        )*/
    }

    fun getChatsByUser() {
        viewModelScope.launch {
            //chatList.value.data = dataRepository.getChatByUser()!!
        }

        /*
        Timer().schedule(object : TimerTask() {
            override fun run() {
                getChatsByUser()
            }
        }, 10)*/
    }
}