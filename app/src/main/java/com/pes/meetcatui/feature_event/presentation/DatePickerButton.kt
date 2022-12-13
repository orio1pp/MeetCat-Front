package com.pes.meetcatui.feature_event.presentation

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import java.time.LocalDate
import java.util.*

@Composable
fun DatePickerButton(
    date: MutableState<LocalDate>,
    context: Context,
){
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    val mCalendar = Calendar.getInstance()

    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    val mDatePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            date.value = LocalDate.of(mYear, mMonth+1, mDayOfMonth)
        }, mYear, mMonth, mDay
    )

    Column(
        modifier = Modifier.width(80.dp).padding(end = 40.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
    ) {
        IconButton(
            onClick = {
                mDatePickerDialog.show()
            },
            modifier = Modifier
                .alpha(1.0f)
                //.padding(start = 16.dp, top = 16.dp)
                .clip(CircleShape)
                .border(2.dp, Color(0xFF838383), shape = CircleShape)
                .background(Color(0xFFBEBEBE)),
        ) {
            Icon(
                imageVector = Icons.Filled.DateRange,
                tint = Color.Black,
                contentDescription = null,
                modifier = Modifier.zIndex(2f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DatePickerPreview() {
    val context = LocalContext.current

    val date = remember { mutableStateOf(LocalDate.now())}

    DatePickerButton(date, context)

    Text(text = "Selected Date: ${date.value}", fontSize = 30.sp, textAlign = TextAlign.Center)
}
