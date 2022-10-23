package com.pes.meetcatui.feature_event.presentation

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
import androidx.compose.ui.tooling.preview.Preview
import com.pes.meetcatui.R
import com.pes.meetcatui.common.BackButton
import com.pes.meetcatui.common.SpaceDp
import com.pes.meetcatui.feature_event.TimeFormatter
import com.pes.meetcatui.ui.theme.typo

const val EventScreenDestination = "Event"

@Composable
fun EventScreen(
    viewModel: EventViewModel,
) {
    val eventState = viewModel._event.value

    if (eventState.isLoading) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Loading...",
                style = typo.h2
            )
        }
    } else {
        if (!eventState.hasError) {
            BackButton()

            val event = eventState.data!!

            EventDetailsContent(
                name = event.name,
                subtitle = event.subtitle,
                description = event.description,
                startDate = event.startDate,
                endDate = event.endDate,
                locationName = event.locationName,
                address = event.address,
                link = event.link
            )
        } else {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Error!",
                    style = typo.h2,
                    color = Color(0xFFA00000))
            }
        }
    }
}

@Composable
private fun EventDetailsItemName(name: String) {
    Text(text = name,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.primary
    )
    SpaceDp(4)
}

@Composable
private fun EventDetailsItem(name: String, content: String) {

    EventDetailsItemName(name)

    Text(
        text = content,
        style = typo.body1,
        textAlign = TextAlign.Justify,
        color = MaterialTheme.colors.primary
    )
}

@Composable
private fun EventDetailsLinkItem(name: String, url: String) {

    EventDetailsItemName(name)

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
private fun EventDetails(
    description: String,
    timeStr: String,
    locationName: String?,
    address: String,
    link: String?
) {
    EventDetailsItem(
        stringResource(R.string.location),
        if (locationName.isNullOrEmpty()) address else "$locationName, $address"
    )

    SpaceDp(12)

    EventDetailsItem(
        stringResource(R.string.time),
        timeStr
    )

    SpaceDp(12)

    EventDetailsItem(
        stringResource(R.string.description),
        description
    )

    SpaceDp(12)

    if (!link.isNullOrEmpty())
        EventDetailsLinkItem(
            stringResource(R.string.link),
            link
        )
}

@Composable
private fun EventDetailsContent(
    name: String,
    subtitle: String?,
    description: String,
    startDate: String,
    endDate: String,
    locationName: String?,
    address: String,
    link: String?
) {
    MaterialTheme() {

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
                    style = typo.h2,
                    color = MaterialTheme.colors.secondary,
                )
                Text(
                    text = subtitle ?: "",
                    style = typo.h3,
                    color = MaterialTheme.colors.secondary
                )
                SpaceDp()
                if (!TimeFormatter()
                        .strLocalDateTime_to_Date(startDate, endDate)
                        .isNullOrEmpty()
                ) {
                    Text(
                        text = TimeFormatter()
                            .strLocalDateTime_to_Date(startDate, endDate)
                            .toString(),
                        style = typo.h4,
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                SpaceDp()
                EventDetails(
                    description,
                    TimeFormatter().strLocalDateTime_to_Time(
                        startDate,
                        endDate,
                        stringResource(R.string.from),
                        stringResource(R.string.to),
                        if (checkSingularHour(startDate)) stringResource(R.string.at_singular) else stringResource(R.string.at),
                        if (checkSingularHour(endDate)) stringResource(R.string.at_singular) else stringResource(R.string.at)
                    ),
                    locationName,
                    address,
                    link
                )
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
}

private fun checkSingularHour(date: String): Boolean{
    return date[11] == '0' && date[12] == '1'
}

@Preview(showBackground = true)
@Composable
fun EventScreenPreview() {
    BackButton()
    EventDetailsContent(
        name = "MeetCat Release Party",
        subtitle = "Just a party",
        description = "All work and no play makes Jack a dull boy\nAll work and no play makes Jack a dull boy\nAll work and no play makes Jack a dull boy",
        startDate = "2000-01-01T01:00:00",
        endDate = "2099-12-31T23:59:59.9999",
        locationName = "FIB",
        address = "C. Jordi Girona 12",
        link = "https://www.youtube.com/watch?v=oYzHlvI7bI8"
    )
}