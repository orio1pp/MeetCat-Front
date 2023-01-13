package com.pes.meetcatui.feature_chat.presentation.screen_user_search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.pes.meetcatui.common.ScreenSelector
import com.pes.meetcatui.feature_user.presentation.screen_normal_login.WarningText
import com.pes.meetcatui.ui.theme.*

@Composable
fun UserSearchScreen(
    viewModel: UserSearchViewModel,
    navToChats: () -> Unit,
    navToUserSearch: () -> Unit,
    navToFriendsList: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Background,
    ) {
        Row(
            modifier = Modifier
                .height(80.dp),
        ) {
            ScreenSelector(navToChats, navToUserSearch, navToFriendsList)
        }
        Row(
            modifier = Modifier
                .height(80.dp)
                .padding(top = 80.dp),
            verticalAlignment = Alignment.Top,
        ) {
            SearchBar(viewModel)
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 160.dp),
        ) {
            item {
                for (user in viewModel.results.value) {
                    Row {
                        user.user?.let {
                            UserBox(
                                name = it.username,
                                about = user.user.about,
                                isFriend = user.isFriend,
                                viewModel = viewModel
                            )
                        }
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 48.dp)
                .padding(top = 140.dp)
        ) {
            WarningText(text = viewModel.warning.value)
        }
    }
}

@Composable
fun SearchBar(
    viewModel: UserSearchViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(color = Background, shape = RectangleShape)
            .padding(horizontal = 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(color = Background_alt, shape = RectangleShape)
                .padding(horizontal = 16.dp)
                .padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .height(96.dp)
                    .fillMaxWidth(0.8f)
                    .border(1.dp, LightGray, CircleShape),
                value = viewModel.searchValue,
                onValueChange = { viewModel.searchValue = it },
                textStyle = typo.body1,
                shape = RoundedCornerShape(
                    topStart = 50.dp,
                    topEnd = 50.dp,
                    bottomEnd = 50.dp,
                    bottomStart = 50.dp
                ),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    disabledTextColor = Gray,
                    backgroundColor = Background,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                singleLine = true
            )
            IconButton(
                modifier = Modifier
                    .size(48.dp)
                    .padding(top = 16.dp, start = 12.dp)
                    .background(Highlight, CircleShape),
                onClick = { viewModel.getUser() }
            ) {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun UserBox(
    name: String,
    about: String?,
    isFriend: Boolean,
    viewModel: UserSearchViewModel
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
            Row(
                modifier = Modifier.padding(16.dp, top = 44.dp),
            ) {
                if (about!= null) {
                    Text(
                        modifier = Modifier
                            .width(256.dp),
                        text = about,
                        color = LightGray,
                        style = typo.body1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .padding(start = 276.dp)
                .padding(vertical = 24.dp)
                .size(32.dp)
        ) {
            if (!isFriend) AddFriendButton(viewModel = viewModel)
            else RemoveFriendButton(viewModel = viewModel)
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
fun AddFriendButton(
    viewModel: UserSearchViewModel
) {
    IconButton(
        modifier = Modifier
            .size(20.dp)
            .background(Highlight, RoundedCornerShape(15.dp)),
        onClick = { viewModel.addFriend() }
    ) {
        Icon(
            Icons.Filled.Add,
            contentDescription = "",
            tint = Color.White,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun RemoveFriendButton(
    viewModel: UserSearchViewModel
) {
    IconButton(
        modifier = Modifier
            .size(32.dp)
            .background(ErrorRed, RoundedCornerShape(15.dp)),
        onClick = { viewModel.removeFriend() }
    ) {
        Icon(
            Icons.Filled.Delete,
            contentDescription = "",
            tint = Color.White,
            modifier = Modifier.fillMaxSize()
        )
    }
}