package com.pes.meetcatui.feature_chat.presentation.screen_chat_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.pes.meetcatui.common.ScreenSelector
import com.pes.meetcatui.commons.presentation.Navigation
import com.pes.meetcatui.feature_chat.presentation.screen_chat.ChatScreen
import com.pes.meetcatui.network.chat.GetChatData
import com.pes.meetcatui.ui.theme.Background
import com.pes.meetcatui.ui.theme.Gray
import com.pes.meetcatui.ui.theme.typo

@Composable
fun ChatListScreen(
    viewModel: ChatListViewModel,
    navToChats: () -> Unit,
    navToUserSearch: () -> Unit,
) {

    val chatList by viewModel.chatList
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Background,
    ) {
        Row(
            modifier = Modifier
                .height(80.dp)
        ) {
            ScreenSelector(navToChats, navToUserSearch, navToChats)
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp),
        ) {
            if (chatList != null
                && chatList.data != null
                && !chatList.hasError
                && chatList.isChatSelected
            ) {
                item {
                    ChatScreen(
                        viewModel = viewModel
                    )
                }
                //viewModel.setIsSelected()
            } else {
                item {
                    if (chatList.data != null) {
                        for (chat in chatList.data!!) {
                            chat.friend?.let {
                                Chat(
                                    sender = it,
                                    viewModel = viewModel,
                                    chat = chat
                                ) { chat: GetChatData ->
                                    viewModel.setSelectedChat(chat)
                                }
                                /*lastMessage = chat.messages.get(chat.messages.size - 1).text*/

                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Chat(
    sender: String,
    viewModel: ChatListViewModel,
    chat: GetChatData,
    onChatClick: (GetChatData) -> Unit,
//lastMessage: String,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(color = Background, shape = RectangleShape)
            .padding(horizontal = 16.dp),

        ) {
        TextButton(
            modifier = Modifier
                .fillMaxSize(),
            onClick = { onChatClick(chat) }) {
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp, top = 24.dp),
                text = sender,
                style = typo.h4,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }


        /*
        IF THERE IS AN UNSEEN MESSAGE DISPLAY THIS (CURRENTLY NOT POSSIBLE)
        Box(
            modifier = Modifier
                .padding(top = 8.dp, start = 16.dp)
                .size(12.dp)
                .background(color = Highlight, shape = CircleShape)
        )*/
        Divider(
            startIndent = 0.dp,
            thickness = 1.dp,
            color = Gray,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}



