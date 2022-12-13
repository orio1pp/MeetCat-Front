package com.pes.meetcatui.feature_chat.presentation.screen_chat

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pes.meetcatui.feature_chat.presentation.screen_chat_list.ChatListViewModel
import com.pes.meetcatui.network.chat.MessageData
import com.pes.meetcatui.ui.theme.Background
import com.pes.meetcatui.ui.theme.Background_alt
import com.pes.meetcatui.ui.theme.Highlight
import com.pes.meetcatui.ui.theme.typo

@Composable
fun ChatScreen(
    viewModel: ChatListViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        ChatHeader(username = "Oriol")

        AllMessages(viewModel = viewModel)

        Row(
            modifier = Modifier
                .height(64.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Texting(viewModel = viewModel)
        }
    }
}

@Composable
fun Texting(
    viewModel: ChatListViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(bottom = 5.dp)
            .background(color = Background_alt, shape = RectangleShape),
    ) {
        val text = remember { mutableStateOf(TextFieldValue()) }
        OutlinedTextField(
            modifier = Modifier
                .padding(16.dp)
                .padding(start = 16.dp)
                .height(32.dp)
                .background(color = Background, RoundedCornerShape(8.dp)),
            value = text.value,
            onValueChange = { newText ->
                text.value = newText
            },
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Start),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Highlight,
                cursorColor = Highlight
            ),
        )
        Box(
            modifier = Modifier
                .padding(start = 332.dp, top = 12.dp)
                .height(36.dp)
                .width(36.dp)
                .background(color = Highlight, shape = CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            IconButton(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { /*viewModel.newMessage(text.value.toString())*/ },
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
        //modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        viewModel.chatList.value.chatSelected?.messageList?.let {
            for (message in viewModel.chatList.value.chatSelected?.messageList!!) {
                MessageX(message = message)
            }
        }
    }
}


@Composable
fun MessageX(
    message: MessageData
) {
    if (message.username.equals(/*viewModel.chatList.value.chatSelected!!.user*/ "a@gmail.com")
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