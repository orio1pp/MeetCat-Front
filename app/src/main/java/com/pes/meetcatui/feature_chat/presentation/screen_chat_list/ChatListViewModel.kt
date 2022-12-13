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
            } else {
                chatList.value = ChatListScreenState(
                    hasError = true,
                    errorMessage = "Couldn't get chats"
                )
            }
        }
    }

    fun setSelectedChat(chat: GetChatData) {
        var newChat: Chat? = null
        viewModelScope.launch {
        val username : String = dataRepository.getUsername()
            newChat = chat.chatId?.let {
                chat.friend?.let { it1 ->
                    chat.chatId?.let { dataRepository.getMessagesByChat(it, 0) }?.let { it2 ->
                        Chat(
                            chatId = it,
                            friend = it1,
                            user = username,
                            messageList = it2
                        )
                    }
                }
            }
            chatList.value = ChatListScreenState(
                isChatSelected = true,
                chatSelected = newChat,
                data = chatList.value.data
            )
        }
    }

    fun setIsSelected() {
        chatList.value = ChatListScreenState(
            isChatSelected = false,
            data = chatList.value.data
        )
    }

    private fun GetChatDataToChat(chat: GetChatData): Chat? {
        var newChat: Chat? = null
        viewModelScope.launch {
            newChat = chat.chatId?.let {
                chat.friend?.let { it1 ->
                    chat.chatId?.let { dataRepository.getMessagesByChat(it, 0) }?.let { it2 ->
                        Chat(
                            chatId = it,
                            friend = it1,
                            messageList = it2
                        )
                    }
                }
            }
        }
        return newChat
    }
}