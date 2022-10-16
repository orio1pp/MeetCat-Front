package com.pes.meetcatui.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SpaceDp(spaceDp: Int = 16){
    Spacer(modifier = Modifier.padding(spaceDp.dp))
}