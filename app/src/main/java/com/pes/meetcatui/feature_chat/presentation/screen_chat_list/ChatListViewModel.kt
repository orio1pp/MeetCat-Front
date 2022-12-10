package com.pes.meetcatui.feature_chat.presentation.screen_chat_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pes.meetcatui.feature_chat.domain.DataRepositoryChats
import com.pes.meetcatui.network.chat.GetChatData
import kotlinx.coroutines.launch

class ChatListViewModel(
    val dataRepository: DataRepositoryChats
) : ViewModel() {

    private val _chatList = mutableStateOf(listOf<GetChatData>())
    val chatList: State<List<GetChatData>> = _chatList

    fun getChatsByUser() {
        viewModelScope.launch {
            _chatList.value = dataRepository.getChatByUser()!!
        }
    }
}