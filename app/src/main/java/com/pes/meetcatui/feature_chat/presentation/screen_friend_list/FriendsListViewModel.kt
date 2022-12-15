package com.pes.meetcatui.feature_chat.presentation.screen_friend_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pes.meetcatui.feature_chat.domain.DataRepositoryChats
import com.pes.meetcatui.feature_chat.domain.UserFriendship
import kotlinx.coroutines.launch

class FriendsListViewModel(
    val dataRepository: DataRepositoryChats
) : ViewModel() {

    private val _friendList = mutableStateOf(listOf<UserFriendship>())
    val friendList: State<List<UserFriendship>> = _friendList

    var username = mutableStateOf("")

    init {
        viewModelScope.launch {
            val list = dataRepository.getFriend()
            if (list != null) {
                var newList: MutableList<UserFriendship> = emptyList<UserFriendship>().toMutableList()
                for (friendship in list) {
                    val hasChat: Boolean = dataRepository.getChatByFriendship(friendship.id) != null
                    newList.add(
                        UserFriendship(
                            friendship = friendship,
                            hasChat = hasChat
                        )
                    )
                    _friendList.value = newList
                }
            }
            username.value = dataRepository.getUsername()
        }

    }
}