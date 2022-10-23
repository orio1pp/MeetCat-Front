package com.pes.meetcatui.ui.theme


import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.pes.meetcatui.R

val Poppins = FontFamily(
    Font(R.font.poppins_black, weight = FontWeight.Black),
    Font(R.font.poppins_bold, weight = FontWeight.Bold),
    Font(R.font.poppins_extrabold, weight = FontWeight.ExtraBold),
    Font(R.font.poppins_extralight, weight = FontWeight.ExtraLight),
    Font(R.font.poppins_light, weight = FontWeight.Light),
    Font(R.font.poppins_medium, weight = FontWeight.Medium),
    Font(R.font.poppins_regular, weight = FontWeight.Normal),
    Font(R.font.poppins_semibold, weight = FontWeight.SemiBold),
    Font(R.font.poppins_thin, weight = FontWeight.Thin),
)

// Set of Material typography styles to start with
val typo = Typography(
    defaultFontFamily = Poppins,
    h1 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp,
        textAlign = TextAlign.Center
    ),

    h2 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        textAlign = TextAlign.Center
    ),

    h3 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),

    h4 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
    ),

    body1 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
    ),

    body2 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
)