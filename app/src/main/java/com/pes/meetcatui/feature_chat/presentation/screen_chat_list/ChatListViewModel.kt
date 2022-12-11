package com.pes.meetcatui.feature_chat.presentation.screen_chat_list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pes.meetcatui.feature_chat.domain.Chat
import com.pes.meetcatui.feature_chat.domain.DataRepositoryChats
import com.pes.meetcatui.network.chat.GetChatData
import kotlinx.coroutines.launch

class ChatListViewModel(
    val dataRepository: DataRepositoryChats
) : ViewModel() {

    val chatList = mutableStateOf(ChatListScreenState())

    init {
        viewModelScope.launch {
            val allChats = dataRepository.getChatByUser(0)?.toMutableList()
            if (allChats != null) {
                println(allChats)
                chatList.value = ChatListScreenState(
                    data = allChats,
                    page = 1
                )
            }
            else {
                chatList.value = ChatListScreenState(
                    hasError = true,
                    errorMessage = "Couldn't get chats"
                )
            }
        }
    }

    fun setSelectedChat(chat: GetChatData) {
        chatList.value = ChatListScreenState(
            isChatSelected = true,
            chatSelected = getChat(),
            data = chatList.value.data
        )
    }

    fun setIsSelected() {
        chatList.value = ChatListScreenState(
            isChatSelected = false,
            data = chatList.value.data
        )
    }

    fun getChat() : Chat {
        return Chat(chatId = 2, friend = "b@gmail.com", messageList = emptyList())
    }
}