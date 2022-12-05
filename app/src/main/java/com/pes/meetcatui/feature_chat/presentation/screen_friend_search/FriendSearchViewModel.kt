package com.pes.meetcatui.feature_chat.presentation.screen_friend_search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.pes.meetcatui.feature_chat.domain.DataRepository
import com.pes.meetcatui.feature_chat.domain.Message

class FriendSearchViewModel(dataRepository: DataRepository) : ViewModel() {

    private val _searchValue = mutableStateOf(listOf<String>())
    val searchValue: State<List<String>> = _searchValue

    private val _results = mutableStateOf(listOf<String>()) //change this to users
    val results: State<List<String>> = _results

}