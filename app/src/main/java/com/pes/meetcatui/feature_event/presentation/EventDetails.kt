package com.pes.meetcatui.feature_event.presentation

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pes.meetcatui.R
import com.pes.meetcatui.common.BackButton
import com.pes.meetcatui.common.SpaceDp
import com.pes.meetcatui.feature_event.TimeFormatter
import com.pes.meetcatui.feature_event.domain.Event
import com.pes.meetcatui.ui.theme.typo

@Composable
fun EventDetails(
    event: Event,
    globalEvent: MutableState<Event?>,
    attendance: EventAttendanceState,
    getIsUsers: () -> Boolean,
    onClickJoin: () -> Unit,
    onClickLeave: () -> Unit,
    deleteEvent: () -> Unit,
    navToEdit: () -> Unit,
    admin:Boolean = false
) {
    EventDetailsContent(
        event = event,
        name = event.name,
        subtitle = event.subtitle,
        username = event.username!!,
        description = event.description,
        startDate = event.startDate,
        endDate = event.endDate,
        placeName = event.placeName,
        address = event.address,
        link = event.link,
        attendeesCount = event.attendeesCount,
        attendance = attendance,
        isUsers = getIsUsers(),
        onClickJoin = onClickJoin,
        onClickLeave = onClickLeave,
        deleteEvent = deleteEvent,
        globalEvent = globalEvent,
        navToEdit = navToEdit,
        admin = admin
    )


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
private fun EventDetailsBody(
    description: String?,
    timeStr: String,
    placeName: String?,
    address: String,
    link: String?
) {
    EventDetailsItem(
        stringResource(R.string.location),
        if (placeName.isNullOrEmpty()) address else "$placeName, $address"
    )

    SpaceDp(12)

    EventDetailsItem(
        stringResource(R.string.time),
        timeStr
    )

    SpaceDp(12)

    if (!description.isNullOrEmpty())
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
    event: Event?,
    name: String,
    subtitle: String?,
    username: String,
    description: String?,
    startDate: String,
    endDate: String?,
    placeName: String?,
    address: String?,
    link: String?,
    attendeesCount: Int,
    attendance: EventAttendanceState,
    isUsers: Boolean,
    onClickJoin: () -> Unit,
    onClickLeave: () -> Unit,
    deleteEvent: () -> Unit,
    globalEvent: MutableState<Event?>,
    navToEdit: () -> Unit,
    admin: Boolean = false
) {
    val attendeesCountState = mutableStateOf(attendeesCount)
    MaterialTheme {
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
                Text(
                    modifier = Modifier
                        .clickable {
                            // TODO: add navigation to this user's profile to see their event list
                        },
                    text = stringResource(R.string.organizer) + ": " + username,
                    style = typo.h4,
                    color = MaterialTheme.colors.secondary,
                    textDecoration = TextDecoration.Underline,
                )
                Text(
                    text = stringResource(R.string.atendees) + ": ${attendeesCountState.value}",
                    style = typo.h4,
                    color = MaterialTheme.colors.secondary
                )
                SpaceDp()
                if (!endDate.isNullOrEmpty() && !TimeFormatter()
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

                EventDetailsBody(
                    description = description,
                    timeStr = if (!endDate.isNullOrEmpty())
                        TimeFormatter().strLocalDateTime_to_Time(
                            startDate,
                            endDate,
                            stringResource(R.string.from),
                            stringResource(R.string.to),
                            if (checkSingularHour(startDate)) stringResource(R.string.at_singular) else stringResource(R.string.at),
                            if (checkSingularHour(endDate)) stringResource(R.string.at_singular) else stringResource(R.string.at)) else "",
                    placeName,
                    address?:"",
                    link
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                SpaceDp()
                Button(
                    modifier = Modifier.width(120.dp),
                    onClick = {
                        if (attendance.isAttended) {
                            onClickLeave()
                            --attendeesCountState.value
                        } else {
                            onClickJoin()
                            ++attendeesCountState.value
                        }
                    },
                    shape = RoundedCornerShape(32.dp)
                ) {
                    Text(
                        text =
                        if (attendance.isAttended)
                            stringResource(id = R.string.leave)
                        else
                            stringResource(id = R.string.join)
                    )
                }
                if (isUsers) {
                    SpaceDp()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(0.5f),
                            horizontalAlignment = Alignment.Start,
                        ) {
                            Button(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(32.dp))
                                    .border(
                                        width = 2.dp,
                                        color = Color(0xFFFFC927),
                                        shape = RoundedCornerShape(32.dp)
                                    )
                                    .width(90.dp)
                                    .height(40.dp),
                                onClick = {
                                    globalEvent.value = event
                                    navToEdit()
                                },
                                shape = RoundedCornerShape(32.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(0x40FFDC00),
                                    contentColor = Color(0xFF5E5E5E),
                                )
                            ) {
                                Text(text = stringResource(id = R.string.edit))
                            }
                        }
                        Column(
                            modifier = Modifier.fillMaxWidth(1f),
                            horizontalAlignment = Alignment.End,
                        ) {
                            Button(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(32.dp))
                                    .border(
                                        width = 2.dp,
                                        color = Color(0x40940000),
                                        shape = RoundedCornerShape(32.dp)
                                    )
                                    .width(90.dp)
                                    .height(40.dp),
                                onClick = deleteEvent,
                                shape = RoundedCornerShape(32.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(0x81FF2A2A),
                                    contentColor = Color(0xFFEEEEEE)
                                )
                            ) {
                                Text(stringResource(id = R.string.delete))
                            }
                        }
                    }
                }
                else if (admin == true)
                {
                    SpaceDp()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(1f),
                            horizontalAlignment = Alignment.End,
                        ) {
                            Button(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(32.dp))
                                    .border(
                                        width = 2.dp,
                                        color = Color(0x40940000),
                                        shape = RoundedCornerShape(32.dp)
                                    )
                                    .width(90.dp)
                                    .height(40.dp),
                                onClick = deleteEvent,
                                shape = RoundedCornerShape(32.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(0x81FF2A2A),
                                    contentColor = Color(0xFFEEEEEE)
                                )
                            ) {
                                Text(stringResource(id = R.string.delete))
                            }
                        }
                    }
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
    EventDetailsContent(
        event = null,
        name = "MeetCat Release Party",
        subtitle = "Just a party",
        username = "Username",
        description = "All work and no play makes Jack a dull boy\nAll work and no play makes Jack a dull boy\nAll work and no play makes Jack a dull boy",
        startDate = "2000-01-01T01:00:00",
        endDate = "2099-12-31T23:59:59.9999",
        placeName = "FIB",
        address = "C. Jordi Girona 12",
        link = "https://www.youtube.com/watch?v=oYzHlvI7bI8",
        attendance = EventAttendanceState(),
        isUsers = true,
        onClickJoin = {},
        onClickLeave = {},
        attendeesCount = 11,
        deleteEvent = {},
        globalEvent = mutableStateOf(null),
        navToEdit = {},
    )
    BackButton()
}