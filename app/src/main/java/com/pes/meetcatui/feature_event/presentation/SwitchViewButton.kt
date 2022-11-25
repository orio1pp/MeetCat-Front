package com.pes.meetcatui.feature_event.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun (() -> Unit).switchViewButton(
    icon: ImageVector,
) {
    FloatingActionButton(
        onClick = this,
        modifier = Modifier
            .alpha(1.0f)
            .padding(top = 16.dp, end = 304.dp)
            .clip(CircleShape)
            .border(2.dp, Color(0xFF838383), shape = CircleShape),
        backgroundColor = Color(0xFFBEBEBE),
        elevation = FloatingActionButtonDefaults.elevation(2.dp),
        ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            tint = Color(0xFF5A5A5A),
        )
    }
}