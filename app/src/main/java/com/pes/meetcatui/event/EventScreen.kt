package com.pes.meetcatui.event

import android.graphics.Paint.Align
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pes.meetcatui.ui.theme.MeetCatUITheme
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.res.stringResource
import com.pes.meetcatui.R

@Composable
fun EventTitle(name: String) {
    Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text(
            text = "Hello $name!",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
        )
    }
}

@Composable
fun EventSubtitle(name: String) {
    Text(
        text = "Hello $name!",
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
    )
}

@Composable
fun EventDate(startDate: String?, endDate: String?) {
    val strStartDate = if(startDate.isNullOrEmpty()) "[ ]" else startDate
    val strEndDate = if(endDate.isNullOrEmpty()) "[ ]" else endDate
    Text(
        text = "$strStartDate - $strEndDate",
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    )
}

@Composable
fun EventDetailsItem(name: String, content: String) {
    Text(text = name,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.padding(3.dp))
    Text(text = content,
        fontSize = 14.sp
    )
}

@Composable
fun EventDetails() {
    EventDetailsItem(stringResource(R.string.location), "C. Jordi Girona, 12")
    Spacer(modifier = Modifier.padding(13.dp))
    val strStartTime = "8:00"
    val strEndTime = "18:00"
    val strTimeInfo = "De $strStartTime a $strEndTime"
    EventDetailsItem(stringResource(R.string.time), strTimeInfo)
    Spacer(modifier = Modifier.padding(13.dp))
    EventDetailsItem(stringResource(R.string.description), "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed in imperdiet dui, ut viverra ante. Morbi in eros tincidunt, convallis lorem in, commodo mauris. In ultricies turpis vel suscipit suscipit. Vestibulum a lectus nisl. Quisque viverra dolor eu nunc elementum consequat. Phasellus sed tempus velit. Vivamus et viverra enim. Proin tempus odio nec quam elementum, vel rutrum felis volutpat. Aenean dapibus consectetur luctus. Duis lacinia sem quis est venenatis, at mollis felis scelerisque.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed in imperdiet dui, ut viverra ante. Morbi in eros tincidunt, convallis lorem in, commodo mauris. In ultricies turpis vel suscipit suscipit. Vestibulum a lectus nisl. Quisque viverra dolor eu nunc elementum consequat. Phasellus sed tempus velit. Vivamus et viverra enim. Proin tempus odio nec quam elementum, vel rutrum felis volutpat. Aenean dapibus consectetur luctus. Duis lacinia sem quis est venenatis, at mollis felis scelerisque.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed in imperdiet dui, ut viverra ante. Morbi in eros tincidunt, convallis lorem in, commodo mauris. In ultricies turpis vel suscipit suscipit. Vestibulum a lectus nisl. Quisque viverra dolor eu nunc elementum consequat. Phasellus sed tempus velit. Vivamus et viverra enim. Proin tempus odio nec quam elementum, vel rutrum felis volutpat. Aenean dapibus consectetur luctus. Duis lacinia sem quis est venenatis, at mollis felis scelerisque.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed in imperdiet dui, ut viverra ante. Morbi in eros tincidunt, convallis lorem in, commodo mauris. In ultricies turpis vel suscipit suscipit. Vestibulum a lectus nisl. Quisque viverra dolor eu nunc elementum consequat. Phasellus sed tempus velit. Vivamus et viverra enim. Proin tempus odio nec quam elementum, vel rutrum felis volutpat. Aenean dapibus consectetur luctus. Duis lacinia sem quis est venenatis, at mollis felis scelerisque.")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MeetCatUITheme {
        IconButton(
            onClick = {},
            modifier = Modifier
                .alpha(1.0f)
                .padding(start = 16.dp, top = 16.dp)
                .clip(CircleShape)
                .border(1.dp, Color(0xFFA0A0A0), shape = CircleShape)
                .background(color = Color(color = 0xFFF4F4F4)),

            ) {

            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = Color(0xFF0E0E0E),
            )
        }
        Column (
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally) {

            Spacer(modifier = Modifier.padding(16.dp))
            EventTitle("PES")
            Spacer(modifier = Modifier.padding(8.dp))
            EventSubtitle("PES2")
            Spacer(modifier = Modifier.padding(8.dp))
            EventDate("13/10/2022", String())
            Spacer(modifier = Modifier.padding(16.dp))
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp, 8.dp, 16.dp), horizontalArrangement = Arrangement.Start) {
                Column (Modifier.fillMaxSize()) {
                    EventDetails()
                }
            }
        }


    }
}