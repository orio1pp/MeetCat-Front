package com.pes.meetcatui.feature_chat.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.util.concurrent.Flow

class ChatListViewModel : ViewModel() {
    private val _friends = mutableStateOf(listOf(""))
    val friends: State<List<String>> = _friends

    fun init() {
        _friends.value = emptyList()

        _friends.value += "a"
        _friends.value += "b"
        _friends.value += "c"
        _friends.value += "d"
        _friends.value += "e"
    }
}