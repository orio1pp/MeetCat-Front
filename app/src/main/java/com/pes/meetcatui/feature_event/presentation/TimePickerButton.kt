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
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.MutableState
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.pes.meetcatui.R
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
        Modifier.padding(horizontal = 5.dp)
    ) {
        IconButton(
            onClick = {
                mTimePickerDialog.show()
            },
            modifier = Modifier
                .alpha(1.0f)
                .clip(CircleShape)
                .border(2.dp, Color(0xFFBEBEBE), shape = CircleShape)
                .background(Color(0xFFBEBEBE))
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.outline_schedule_24),
                tint = Color.Black,
                contentDescription = null,
                modifier = Modifier.size(35.dp),
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
