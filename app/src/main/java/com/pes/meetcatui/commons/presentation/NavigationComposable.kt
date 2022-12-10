package com.pes.meetcatui.commons.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.pes.meetcatui.ui.theme.Background_alt
import com.pes.meetcatui.ui.theme.Gray
import com.pes.meetcatui.ui.theme.Highlight
import com.pes.meetcatui.ui.theme.LightGray


@Composable
fun Navigation(
    section: String
) {
    Column() {
        Divider(
            modifier = Modifier.background(Brush.verticalGradient(colors = listOf(Color.White, LightGray))),
            thickness = 6.dp,
        )
        Row(
            Modifier
                .background(color = Background_alt, shape = RectangleShape)
                .height(64.dp)
                //.shadow(elevation = 1.dp, shape = RectangleShape)
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            val mod: Modifier = Modifier
                .weight(1f)

            val tintList = Array<Color>(5) { Gray }
            when (section) {
                "map" -> tintList[0] = Highlight
                "calendar" -> tintList[1] = Highlight
                "profile" -> tintList[2] = Highlight
                "chat" -> tintList[3] = Highlight
                "settings" -> tintList[4] = Highlight
            }

            NavigationIcon(icon = Icons.Rounded.LocationOn, modifier = mod, tint = tintList[0])
            NavigationIcon(icon = Icons.Rounded.DateRange, modifier = mod, tint = tintList[1])
            NavigationIcon(icon = Icons.Rounded.Person, modifier = mod, tint = tintList[2])
            NavigationIcon(icon = Icons.Rounded.MailOutline, modifier = mod, tint = tintList[3])
            NavigationIcon(icon = Icons.Rounded.Settings, modifier = mod, tint = tintList[4])

        }
    }

}

@Composable
fun NavigationIcon(
    icon: ImageVector,
    modifier: Modifier,
    tint: Color
    //clickHandler: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = { /*clickHandler*/ },
    ) {
        Icon(
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .fillMaxHeight(0.75f),
            imageVector = icon,
            contentDescription = "",
            tint = tint
        )
    }
}