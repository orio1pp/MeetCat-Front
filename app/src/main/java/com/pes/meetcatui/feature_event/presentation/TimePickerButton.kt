package com.pes.meetcatui.feature_event.presentation

import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.MutableState
import java.time.LocalTime
import java.util.*

@Composable
fun TimePickerButton(
    time: MutableState<LocalTime>,
    context: Context,
){
    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]

    val mTimePickerDialog = TimePickerDialog(
        context,
        {_, mHour : Int, mMinute: Int ->
            time.value = LocalTime.of(mHour, mMinute)
        }, mHour, mMinute, false
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = { mTimePickerDialog.show() }
        ) {
            Icon(
                imageVector = Icons.Filled.ShoppingCart,
                contentDescription = null,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val context = LocalContext.current
    val time = remember { mutableStateOf(LocalTime.now()) }
    TimePickerButton(time, context)
    Text(text = "Selected Time: ${time.value}", fontSize = 30.sp)
}
