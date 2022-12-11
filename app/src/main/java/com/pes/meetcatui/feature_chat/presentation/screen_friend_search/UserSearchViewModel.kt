package com.pes.meetcatui.feature_chat.presentation.screen_friend_search

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pes.meetcatui.feature_chat.domain.FriendshipHelper
import com.pes.meetcatui.feature_user.domain.DataRepositoryUsers
import com.pes.meetcatui.network.UserData
import kotlinx.coroutines.launch

class UserSearchViewModel(
    val dataRepository: DataRepositoryUsers
) : ViewModel() {

    var searchValue by mutableStateOf("")

    private val _results = mutableStateOf(listOf<FriendshipHelper>())
    val results: State<List<FriendshipHelper>> = _results

    private val _warning = mutableStateOf("")
    val warning: State<String> = _warning

    fun getUser() {
        viewModelScope.launch {
            val user: UserData? = dataRepository.getUser(searchValue)
            if (user != null) {
                val friends = dataRepository.getFriend()
                _warning.value = ""
                var list: MutableList<FriendshipHelper> =
                    emptyList<FriendshipHelper>().toMutableList()
                var isFriend = false
                if (friends != null) {
                    for (fr in friends)
                        if (fr.friendId.equals(user.username))
                            isFriend = true
                }
                list.add(FriendshipHelper(user, isFriend))
                _results.value = list
            } else {
                _results.value = emptyList()
                _warning.value = "No hi ha usuaris amb aquest nom d'usuari"
            }
        }
    }

    fun addFriend() {
        viewModelScope.launch {
            _results.value[0].user?.let { dataRepository.addFriend(it.username) }
        }
    }

    fun removeFriend() {
        viewModelScope.launch {
            _results.value[0].user?.let { dataRepository.removeFriend(it.username) }
        }
    }
}