package com.pes.meetcatui.feature_chat.presentation.screen_chat

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.pes.meetcatui.feature_chat.domain.DataRepositoryChats
import com.pes.meetcatui.network.chat.MessageData

class ChatViewModel(dataRepository: DataRepositoryChats) : ViewModel (

) {

    private val _messages = mutableStateOf(listOf<MessageData>())
    val messages: State<List<MessageData>> = _messages

    fun init() {
        /*
        _messages.value = emptyList()
        _messages.value += MessageData(0, "oriol", Date(2022, 0, 2), "aaaa \n ddsss")
        _messages.value += MessageData(
            1,
            "alex",
            Date(2022, 0, 2),
            "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb"
        )
        _messages.value += MessageData(2, "oriol", Date(2022, 0, 2), "cccc")
        _messages.value += MessageData(3, "alex", Date(2022, 0, 2), "dddd")*/

    }

    fun newMessage(text: String) {
        //_messages.value += MessageData(4, "oriol", Date.from(Instant.now()), text)
        //text = ""
    }
}