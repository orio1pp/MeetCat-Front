package com.pes.meetcatui.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Background_alt,
    primaryVariant = Gray,
    secondary = LightGray
)

private val LightColorPalette = lightColors(
    primary = Background,
    primaryVariant = LightGray,
    secondary = Gray
)

@Composable
fun MeetCatUITheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = typo,
        shapes = Shapes,
        content = content
    )
}