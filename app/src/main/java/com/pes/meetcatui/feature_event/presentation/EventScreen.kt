package com.pes.meetcatui.feature_event.presentation

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import com.pes.meetcatui.R
import com.pes.meetcatui.common.BackButton
import com.pes.meetcatui.common.SpaceDp
import com.pes.meetcatui.ui.theme.typo

const val EventScreenDestination = "Event"
@Composable
fun EventDates(startDate: String?, endDate: String?) {
    Text(
        text = "$startDate - $endDate",
        style = typo.h4,
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
        style = typo.body1,
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
fun EventDetails(description: String, startTime: String, endTime: String, locationName: String?, address: String, link: String?) {
    EventDetailsItem(stringResource(R.string.location),  if (locationName.isNullOrEmpty()) address else "$locationName, \n $address")
    SpaceDp(12)
    val strTimeInfo = "De $startTime a $endTime"
    EventDetailsItem(stringResource(R.string.time), strTimeInfo)
    SpaceDp(12)
    EventDetailsItem(stringResource(R.string.description), description)
    SpaceDp(12)
    if (!link.isNullOrEmpty()) EventDetailsLinkItem(stringResource(R.string.link), link)
}

@Composable
fun EventDetailsContent(
    name: String,
    subtitle: String?,
    description: String,
    startDate: String?,
    endDate: String?,
    locationName: String?,
    address: String,
    link: String?
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
                text = name,
                style = typo.h2
            )
            Text(
                text = "$subtitle",
                style = typo.h3
            )
            EventDates(startDate = startDate, endDate = endDate)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            SpaceDp()
            EventDetails(description, startDate?: "[ ]", endDate?: "[ ]", locationName, address, link)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            SpaceDp()
            Button(
                onClick = {},
            ) {
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

    BackButton()
    EventDetailsContent(
        name = event?.name ?: "MeetCat Release Party",
        subtitle = event?.subtitle,
        description = event?.description ?: "All work and no play makes Jack a dull boy\nAll work and no play makes Jack a dull boy\nAll work and no play makes Jack a dull boy\n",
        startDate = event?.startDate,
        endDate = event?.endDate,
        locationName = event?.locationName,
        address = event?.address ?: "C. Jordi Girona 12",
        link = event?.link
    )
}