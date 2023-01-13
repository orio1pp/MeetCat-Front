package com.pes.meetcatui.feature_chat.presentation.screen_chat_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pes.meetcatui.feature_chat.domain.Chat
import com.pes.meetcatui.feature_chat.domain.DataRepositoryChats
import com.pes.meetcatui.network.chat.GetChatData
import com.pes.meetcatui.network.chat.MessageData
import kotlinx.coroutines.launch
import java.util.*

class ChatListViewModel(
    val dataRepository: DataRepositoryChats
) : ViewModel() {

    val chatList = mutableStateOf(ChatListScreenState())
    var newMessage by mutableStateOf("")

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
            val username: String = dataRepository.getUsername()
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
                chatSelected = mutableStateOf(newChat),
                data = chatList.value.data
            )
        }
        Timer().schedule(object : TimerTask() {
            override fun run() {
                if (chatList.value.isChatSelected)
                    setSelectedChat(chat)
            }
        }, 1000)
    }

    fun setIsSelected() {
        chatList.value = ChatListScreenState(
            isChatSelected = false,
            data = chatList.value.data
        )
    }

    fun sendMessage() {
        viewModelScope.launch {
            dataRepository.newMessage(
                MessageData(
                    messageId = null,
                    text = newMessage,
                    date = null,
                    chatId = chatList.value.chatSelected?.value?.chatId,
                    username = dataRepository.getUsername()
                )
            )
        }
        newMessage = ""
    }
}