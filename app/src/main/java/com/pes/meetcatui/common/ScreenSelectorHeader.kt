package com.pes.meetcatui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.pes.meetcatui.ui.theme.Background
import com.pes.meetcatui.ui.theme.Background_alt
import com.pes.meetcatui.ui.theme.Gray
import com.pes.meetcatui.ui.theme.typo

@Composable
fun ScreenSelector() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Background, shape = RectangleShape)
            .padding(horizontal = 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(color = Background_alt, shape = RectangleShape)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                FriendNavigationButton("Chats", Icons.Filled.Email)
            }
            Column(
                modifier = Modifier.weight(1f)
            ) {
                FriendNavigationButton("Search", Icons.Filled.Search)
            }
            Column(
                modifier = Modifier.weight(1f)
            ) {
                FriendNavigationButton("Friends", Icons.Filled.Person)
            }
        }
    }
}

@Composable
fun FriendNavigationButton(
    text: String,
    icon: ImageVector
) {
    TextButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp)
            .background(color = Background, shape = CircleShape)
            .border(1.dp, Gray, CircleShape),
        onClick = { },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White.copy(
                alpha = 0F,
            )
        )
    ) {
        Icon(
            icon,
            contentDescription = "",
            tint = Gray,
            modifier = Modifier.size(24.dp)
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp),
            text = text,
            color = Color.Black,
            style = typo.body1
        )
    }
}