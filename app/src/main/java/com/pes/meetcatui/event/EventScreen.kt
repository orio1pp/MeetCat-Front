package com.pes.meetcatui.event

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pes.meetcatui.ui.theme.MeetCatUITheme
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import com.pes.meetcatui.R
import com.pes.meetcatui.common.BackButton
import com.pes.meetcatui.common.SpaceDp

const val EventScreenDestination = "Event"
@Composable
fun EventDate(startDate: String = "[ ]", endDate: String = "[ ]") {
    Text(
        text = "$startDate - $endDate",
        style = MaterialTheme.typography.h4,
    )
}

@Composable
fun EventDetailsItem(name: String, content: String) {
    Text(text = name,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
    SpaceDp(4)
    Text(
        text = content,
        style = MaterialTheme.typography.body1,
        textAlign = TextAlign.Justify
    )
}

@Composable
fun EventDetailsLinkItem(name: String, url: String) {
    Text(text = name,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
    SpaceDp(4)
    val context = LocalContext.current
    Text(
        text = url,
        style = TextStyle(
            fontSize = 16.sp,
            textDecoration = TextDecoration.Underline,
            color = Color(color = 0xFF0645AD),
            textAlign = TextAlign.Start
        ),
        modifier = Modifier.clickable {
            CustomTabsIntent.Builder().build().launchUrl(context, Uri.parse(url))
        }
    )
}

@Composable
fun EventDetails(eventDescription: String) {
    EventDetailsItem(stringResource(R.string.location), "C. Jordi Girona, 12")
    SpaceDp(12)
    val strStartTime = "8:00"
    val strEndTime = "18:00"
    val strTimeInfo = "De $strStartTime a $strEndTime"
    EventDetailsItem(stringResource(R.string.time), strTimeInfo)
    SpaceDp(12)
    EventDetailsItem(stringResource(R.string.description), eventDescription)
    SpaceDp(12)
    EventDetailsLinkItem(stringResource(R.string.link), "https://www.youtube.com/watch?v=Hvtc9BWahMQ")
}

@Composable
fun EventDetailsContent(
    eventName: String,
    eventDescription: String,
    eventDate: String
) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = eventName,
                style = MaterialTheme.typography.h2
            )
            Text(
                text = "A party",
                style = MaterialTheme.typography.h3
            )
            EventDate(startDate = eventDate)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            SpaceDp()
            EventDetails(eventDescription)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            SpaceDp()
            Button(onClick = {}) {
                Text("Join placeholder")
            }
        }
    }
}

@Composable
fun EventScreen(
    viewModel: EventViewModel,
) {
    val event by viewModel.event.collectAsState()

    MeetCatUITheme {
        BackButton()
        EventDetailsContent(
            eventName = event?.name ?: "MeetCat Release Party",
            eventDescription = event?.description ?: "All work and no play makes Jack a dull boy\nAll work and no play makes Jack a dull boy\nAll work and no play makes Jack a dull boy\n",
            eventDate = event?.date ?: "03/11/2999",
        )
    }
}