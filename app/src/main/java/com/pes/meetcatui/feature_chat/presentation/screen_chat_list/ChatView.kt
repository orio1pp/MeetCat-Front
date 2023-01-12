package com.pes.meetcatui.feature_chat.presentation.screen_chat_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.pes.meetcatui.network.chat.MessageData
import com.pes.meetcatui.ui.theme.*

@Composable
fun ChatScreen(
    viewModel: ChatListViewModel
) {
    Column(
    ) {
        viewModel.chatList.value.chatSelected?.value?.friend.let {
            if (it != null) {
                ChatHeader(username = it)
            }
        }

        AllMessages(viewModel = viewModel)


    }/*
    Row(
        verticalAlignment = Alignment.Bottom
    ) {
        Texting(viewModel = viewModel)
    }*/
}

@Composable
fun Texting(
    viewModel: ChatListViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(color = Background_alt, shape = RectangleShape),
    ) {
        OutlinedTextField(
            modifier = Modifier
                .padding(10.dp)
                .padding(start = 16.dp),
            value = viewModel.newMessage,
            onValueChange = { newText ->
                viewModel.newMessage = newText
            },
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
        Box(
            modifier = Modifier
                .padding(start = 332.dp, top = 24.dp)
                .height(36.dp)
                .width(36.dp)
                .background(color = Highlight, shape = CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            IconButton(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { viewModel.sendMessage() },
            ) {
                Icon(
                    imageVector = Icons.Rounded.Send,
                    contentDescription = "send icon",
                    tint = Color.White,
                )
            }
        }
    }
}

@Composable
fun AllMessages(
    viewModel: ChatListViewModel
) {
    Column(
        modifier = Modifier.padding(bottom = 100.dp)
    ) {
        viewModel.chatList.value.chatSelected?.value?.messageList?.let {
            for (message in viewModel.chatList.value.chatSelected?.value?.messageList!!) {
                MessageX(message = message, viewModel = viewModel)
            }
        }
    }
}


@Composable
fun MessageX(
    message: MessageData,
    viewModel: ChatListViewModel
) {
    if (message.username.equals(viewModel.chatList.value.chatSelected!!.value?.user)
    ) {
        MessageSent(date = message.date.toString(), text = message.text!!)
    } else {
        MessageReceived(date = message.date.toString(), text = message.text!!)
    }
}

@Composable
fun MessageSent(
    date: String,
    text: String,
) {
    Box(
        modifier = Modifier
            .padding(vertical = 24.dp)
            .padding(start = 96.dp, end = 16.dp)
            .fillMaxWidth()
            .defaultMinSize(minWidth = 124.dp, minHeight = 64.dp)
            .background(
                color = Highlight,
                shape = RoundedCornerShape(corner = CornerSize(8.dp))
            ),
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
        )
        {
            Row {
                Text(
                    color = Color.White,
                    text = date,
                    style = typo.body2
                )
            }
            Row {
                Text(
                    color = Color.White,
                    text = text,
                    style = typo.body1
                )
            }
        }
    }
}

@Composable
fun MessageReceived(
    date: String,
    text: String,
) {
    Box(
        modifier = Modifier
            .padding(vertical = 24.dp)
            .padding(start = 16.dp, end = 96.dp)
            .fillMaxWidth()
            .defaultMinSize(minWidth = 124.dp, minHeight = 64.dp)
            .background(
                color = Background_alt,
                shape = RoundedCornerShape(corner = CornerSize(8.dp))
            ),
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
        )
        {
            Row {
                Text(
                    text = date,
                    style = typo.body2
                )
            }
            Row {
                Text(
                    text = text,
                    style = typo.body1
                )
            }
        }

    }
}

@Composable
fun ChatHeader(
    username: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(color = Background_alt, shape = RectangleShape),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            modifier = Modifier.padding(12.dp),
            text = username,
            style = typo.h4
        )
    }
}