package com.pes.meetcatui.feature_chat.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.pes.meetcatui.feature_chat.domain.Message
import java.time.Instant
import java.time.LocalDate
import java.util.*

class ChatViewModel : ViewModel() {
    private val _messages = mutableStateOf(listOf<Message>())
    val messages: State<List<Message>> = _messages

    fun init() {
        _messages.value = emptyList()
        _messages.value += Message(0, "oriol", Date(2022, 0,2), "aaaa \n ddsss")
        _messages.value += Message(1, "alex", Date(2022, 0,2), "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb")
        _messages.value += Message(2, "oriol", Date(2022, 0,2), "cccc")
        _messages.value += Message(3, "alex", Date(2022, 0,2), "dddd")

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun newMessage(text: String) {
        _messages.value += Message(4, "oriol", Date.from(Instant.now()), text)
        //text = ""
    }
}