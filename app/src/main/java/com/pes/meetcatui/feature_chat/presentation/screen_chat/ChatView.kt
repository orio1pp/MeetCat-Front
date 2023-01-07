package com.pes.meetcatui.feature_chat.presentation.screen_chat

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pes.meetcatui.commons.presentation.Navigation
import com.pes.meetcatui.feature_chat.presentation.screen_chat_list.ChatListViewModel
import com.pes.meetcatui.ui.theme.Background
import com.pes.meetcatui.ui.theme.Background_alt
import com.pes.meetcatui.ui.theme.Highlight
import com.pes.meetcatui.ui.theme.typo

@Composable
fun ChatScreen(
    viewModel: ChatViewModel,
) {
    //val viewModel = ChatViewModel(get())
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            item {
                ChatHeader(username = "Oriol")
            }
            //viewModel.init()
            item {
                for (message in viewModel.messages.value) {
                    if (message.username.equals("oriol")) {
                        MessageSent(date = message.date.toString(), text = message.text)
                    }
                    else {
                        MessageReceived(date = message.date.toString(), text = message.text)
                    }
                }
            }
        }
        Row (
            modifier = Modifier
                .padding(bottom = 64.dp)
                .height(64.dp)
                .fillMaxHeight(0.2F),
            verticalAlignment = Alignment.Bottom
        ) {
            Texting(viewModel = viewModel)
        }
        Row (
            modifier = Modifier.fillMaxHeight(0.2F),
            verticalAlignment = Alignment.Bottom,
        ) {
            Navigation(section = "chat")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Texting(
    viewModel: ChatViewModel
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
                cursorColor = Highlight),
        )
        Box(
            modifier = Modifier
                .padding(start = 332.dp, top = 12.dp)
                .height(36.dp)
                .width(36.dp)
                .background(color =Highlight, shape = CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            IconButton(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { viewModel.newMessage(text.value.toString()) },
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