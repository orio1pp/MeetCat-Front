package com.pes.meetcatui.feature_chat.presentation.screen_friend_search

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pes.meetcatui.feature_chat.domain.DataRepository
import com.pes.meetcatui.feature_chat.domain.Message
import com.pes.meetcatui.feature_user.domain.DataRepositoryUsers
import com.pes.meetcatui.network.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FriendSearchViewModel(
    val dataRepository: DataRepositoryUsers
    ) : ViewModel() {

    var searchValue by mutableStateOf("")

    private val _results = mutableStateOf(listOf<UserData>())
    val results : State<List<UserData>> = _results

    private val _warning = mutableStateOf("")
    val warning: State<String> = _warning

    fun getUser() {
        viewModelScope.launch {
            val user: UserData? = dataRepository.getUser(searchValue)
            if (user != null) {
                _warning.value = ""
                var list: MutableList<UserData> = emptyList<UserData>().toMutableList()
                list.add(user)
                _results.value = list
            }
            else {
                _results.value = emptyList()
                _warning.value = "No hi ha usuaris amb aquest nom d'usuari"
            }
        }
    }

}