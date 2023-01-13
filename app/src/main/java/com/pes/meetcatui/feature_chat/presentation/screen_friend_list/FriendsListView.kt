package com.pes.meetcatui.feature_chat.presentation.screen_friend_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.pes.meetcatui.common.ScreenSelector
import com.pes.meetcatui.feature_chat.presentation.screen_user_search.UserSearchViewModel
import com.pes.meetcatui.ui.theme.*

@Composable
fun FriendsListScreen(
    viewModel: FriendsListViewModel,
    navToChats: () -> Unit,
    navToUserSearch: () -> Unit,
    navToFriendsList: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Background,
    ) {
        val friendList by viewModel.friendList
        LazyColumn() {
            item {
                Row(
                    modifier = Modifier
                        .height(80.dp),
                ) {
                    ScreenSelector(navToChats, navToUserSearch, navToFriendsList)
                }
            }
            item {
                for (friend in friendList) {
                    Row {
                        friend.let {
                            var friendo = it.friendship.friendId
                            if (viewModel.username.value.equals(it.friendship.friendId))
                                friendo = it.friendship.ownerId
                            FriendBox(
                                name = friendo,
                                id = it.friendship.id,
                                hasChat = it.hasChat,
                                viewModel = viewModel
                            )
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun FriendBox(
    name: String,
    id: Long,
    hasChat: Boolean,
    viewModel: FriendsListViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(color = Background, shape = RectangleShape)
            .padding(horizontal = 16.dp)
    ) {
        Column() {
            Row(
                modifier = Modifier.padding(16.dp, top = 16.dp),
            ) {
                Text(
                    modifier = Modifier
                        .width(256.dp),
                    text = name,
                    style = typo.h4,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(start = 276.dp)
                .padding(vertical = 24.dp)
                .size(32.dp)
        ) {

            if (!hasChat) AddChatButton(viewModel = viewModel, id = id)
            else RemoveChatButton(viewModel = viewModel, id = id)
        }
        Divider(
            startIndent = 0.dp,
            thickness = 1.dp,
            color = Gray,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}


@Composable
fun AddChatButton(
    viewModel: FriendsListViewModel,
    id: Long,
) {
    IconButton(
        modifier = Modifier
            .size(20.dp)
            .background(Highlight, RoundedCornerShape(15.dp)),
        onClick = { viewModel.addChat(id) }
    ) {
        Icon(
            Icons.Filled.Email,
            contentDescription = "",
            tint = Color.White,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun RemoveChatButton(
    viewModel: FriendsListViewModel,
    id: Long,
) {
    IconButton(
        modifier = Modifier
            .size(32.dp)
            .background(ErrorRed, RoundedCornerShape(15.dp)),
        onClick = {  }
    ) {
        Icon(
            Icons.Filled.Delete,
            contentDescription = "",
            tint = Color.White,
            modifier = Modifier.fillMaxSize()
        )
    }
}