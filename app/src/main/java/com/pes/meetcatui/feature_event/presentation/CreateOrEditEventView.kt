package com.pes.meetcatui.feature_event.presentation

import android.content.Context
import android.webkit.URLUtil
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pes.meetcatui.R
import com.pes.meetcatui.feature_event.domain.Event
import com.pes.meetcatui.ui.theme.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

/*
name = "MeetCat Release Party",
subtitle = "Just a party",
description = "All work and no play makes Jack a dull boy\nAll work and no play makes Jack a dull boy\nAll work and no play makes Jack a dull boy",
startDate = "2000-01-01T01:00:00",
endDate = "2099-12-31T23:59:59.9999",
placeName = "FIB",
address = "C. Jordi Girona 12",
link = "https://www.youtube.com/watch?v=oYzHlvI7bI8",
*/

val backgroundColor = Color(0xFFD0D0D0)
val focusedLabelColor = Color(0xFF000000)
val unfocusedLabelColor = Color(0xFF707070)

@Composable
fun CreateOrEditEventView(
    viewModel: CreateOrEditEventViewModel,
    event: Event?,
    navToEvents: () -> Unit,
) {
    val isNull = event == null

    var name: String by remember { mutableStateOf(if (isNull) "" else event!!.name) }
    var subtitle: String by remember { mutableStateOf(if (isNull) "" else event!!.subtitle!!) }
    var description: String by remember { mutableStateOf(if (isNull) "" else event!!.description!!) }

    val startDate = remember { mutableStateOf(if (isNull) LocalDate.now() else LocalDateTime.parse(event!!.startDate).toLocalDate()) }
    val startTime = remember { mutableStateOf(if (isNull) LocalTime.now() else LocalDateTime.parse(event!!.startDate).toLocalTime()) }

    val endDate = remember { mutableStateOf(if (isNull) LocalDate.now() else LocalDateTime.parse(event!!.endDate).toLocalDate()) }
    val endTime = remember { mutableStateOf(if (isNull) LocalTime.now() else LocalDateTime.parse(event!!.endDate).toLocalTime()) }

    var location: String by remember { mutableStateOf(if (isNull) "" else event!!.location!!) }
    var place: String by remember { mutableStateOf(if (isNull) "" else event!!.placeName!!) }
    var address: String by remember { mutableStateOf(if (isNull) "" else event!!.address!!) }
    var link: String by remember { mutableStateOf(if (isNull) "" else event!!.link!!) }

    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            Modifier
                .padding(vertical = 16.dp, horizontal = 35.dp)
                .verticalScroll(state = ScrollState(0)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Row(Modifier.padding(vertical = 8.dp)) {
                var labelText = stringResource(R.string.name)

                name = TextFieldLabeled(name, labelText)
            }
            Row(Modifier.padding(vertical = 8.dp)) {
                var labelText = stringResource(R.string.subtitle)

                subtitle = TextFieldLabeled(subtitle, labelText)
            }
            Row(Modifier.padding(vertical = 8.dp)) {
                var labelText = stringResource(R.string.description)

                description = TextFieldLabeled(description, labelText)
            }
            Row(Modifier.padding(vertical = 8.dp)) {
                var labelText = stringResource(R.string.from)

                TextFieldDate(context, startDate, labelText)
            }
            Row(Modifier.padding(vertical = 8.dp)) {
                var labelText = stringResource(R.string.at)

                TextFieldTime(context, startTime, labelText)
            }
            Row(Modifier.padding(vertical = 8.dp)) {
                var labelText = stringResource(R.string.to)

                TextFieldDate(context, endDate, labelText)
            }
            Row(Modifier.padding(vertical = 8.dp)) {
                var labelText = stringResource(R.string.at)

                TextFieldTime(context, endTime, labelText)
            }
            Row(Modifier.padding(vertical = 8.dp)) {
                var labelText = stringResource(R.string.location)

                location = TextFieldLabeled(location, labelText)
            }
            Row(Modifier.padding(vertical = 8.dp)) {
                var labelText = stringResource(R.string.place)

                place = TextFieldLabeled(place, labelText)
            }
            Row(Modifier.padding(vertical = 8.dp)) {
                var labelText = stringResource(R.string.address)

                address = TextFieldLabeled(address, labelText)
            }
            Row(Modifier.padding(vertical = 8.dp)) {
                var labelText = stringResource(R.string.link)

                link = TextFieldLabeled(link, labelText)
            }
            Row(Modifier.padding(vertical = 8.dp)) {
                CreateButton(
                    viewModel,
                    if (isNull) null else event!!.eventId,
                    name,
                    subtitle,
                    description,
                    LocalDateTime.of(startDate.value, startTime.value),
                    LocalDateTime.of(endDate.value, endTime.value),
                    location,
                    place,
                    address,
                    link,
                    if (isNull) 0 else event!!.attendeesCount,
                    isNull,
                    navToEvents
                )
            }
        }
    }
}

@Composable
private fun CreateButton(
    viewModel: CreateOrEditEventViewModel,
    id: Long?,
    name: String,
    subtitle: String,
    description: String,
    startDate: LocalDateTime,
    endDate: LocalDateTime,
    location: String,
    place: String,
    address: String,
    link: String,
    attendeesCount: Int,
    isNull: Boolean,
    navToEvents: () -> Unit
) {
    var errorStringId: Int by remember { mutableStateOf(0) }
    var errorString: String by remember { mutableStateOf("") }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (errorStringId == 1)
            errorString = stringResource(R.string.createEventErrorFieldsEmpty);
        else if (errorStringId == 2)
            errorString = stringResource(R.string.createEventErrorURLIsNotValid);
        else if (errorStringId == 3)
            errorString = stringResource(R.string.createEventInitDateGreaterThanEnd);
        else if (errorStringId == 4)
            errorString = stringResource(R.string.createEventErrorLocationIsNotCorrectFormat);
        else if (errorStringId == 5)
            errorString = stringResource(R.string.createEventInitDateIsPast);
        else
            errorString = "";


        if (errorStringId != 0) {
            Text(
                errorString,
                color = ErrorRed
            )
        }
        Button(
            onClick = {
                errorStringId = checkInput(
                    name,
                    subtitle,
                    description,
                    startDate,
                    endDate,
                    location,
                    place,
                    address,
                    link
                )
                if (errorStringId == 0) {
                    if (isNull)
                        viewModel.createEvent(
                            name,
                            subtitle,
                            description,
                            startDate.toString(),
                            endDate.toString(),
                            location,
                            place,
                            address,
                            link
                        )
                    else
                        viewModel.updateEvent(
                            id!!,
                            name,
                            subtitle,
                            description,
                            startDate.toString(),
                            endDate.toString(),
                            location,
                            place,
                            address,
                            link,
                            attendeesCount,
                        )
                    navToEvents();
                }
            },
            Modifier
                .height(70.dp)
                .padding(vertical = 8.dp)
                .clip(RoundedCornerShape(30.dp)),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = backgroundColor,
                contentColor = focusedLabelColor
            )
        ) {
            Text(
                if (isNull)
                    stringResource(id = R.string.create)
                else
                    stringResource(id = R.string.edit))
        }
    }
}

@Composable
private fun TextFieldLabeled(
    previewText: String,
    labelText: String,
): String {
    var text: String by remember { mutableStateOf(previewText) }
    var backgndColor: Color by remember { mutableStateOf(backgroundColor) }
    TextField(
        modifier = Modifier
            .width(320.dp)
            .height(70.dp)
            .clip(RoundedCornerShape(25.dp)),
        value = text,
        onValueChange = { newText ->
            text = newText;
        },
        textStyle = typo.h4,
        label = {
            Text(labelText)
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = backgndColor,
            focusedLabelColor = focusedLabelColor,
            unfocusedLabelColor = unfocusedLabelColor
        ),
    )
    return text;
}

@Composable
private fun TextFieldDate(
    context: Context,
    date: MutableState<LocalDate>,
    labelText: String,
) {

    var backgndColor: Color by remember { mutableStateOf(backgroundColor) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextField(
            modifier = Modifier
                .width(262.dp)
                .height(70.dp)
                .clip(RoundedCornerShape(25.dp)),
            value = "${date.value.dayOfMonth}/${date.value.monthValue}/${date.value.year}",
            onValueChange = {},
            textStyle = typo.h4,
            label = {
                Text(labelText)
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = backgndColor,
                focusedLabelColor = focusedLabelColor,
                unfocusedLabelColor = unfocusedLabelColor
            ),
            enabled = false,
        )

        DatePickerButton(date = date, context = context)
    }
}

@Composable
private fun TextFieldTime(
    context: Context,
    time: MutableState<LocalTime>,
    labelText: String,
) {

    var backgndColor: Color by remember { mutableStateOf(backgroundColor) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextField(
            modifier = Modifier
                .width(262.dp)
                .height(70.dp)
                .clip(RoundedCornerShape(25.dp)),
            value = "${time.value.hour}:${time.value.minute}",
            onValueChange = {},
            textStyle = typo.h4,
            label = {
                Text(labelText)
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = backgndColor,
                focusedLabelColor = focusedLabelColor,
                unfocusedLabelColor = unfocusedLabelColor
            ),
            enabled = false,
        )
        TimePickerButton(time = time, context = context)
    }
}

private fun checkInput(name: String, subtitle: String, description: String,
                       startDate: LocalDateTime, endDate: LocalDateTime, location:String, place: String,
                       address: String, link: String) : Int{
    if (!(name != "" && subtitle != "" && description != "" && location != "" && place != "" && address != "" && link != ""))
        return 1//stringResource(R.string.createEventErrorFieldsEmpty);
    else if (!URLUtil.isValidUrl(link))
        return 2//stringResource(R.string.createEventErrorURLIsNotValid);
    val coords = location.split(',')
    try {
        coords[0].toDouble()
        coords[1].toDouble()
    } catch (e: java.lang.NumberFormatException)
    {
        return 4//stringResource(R.string.createEventErrorLocationIsNotCorrectFormat);
    }
    if (startDate >= endDate)
        return 3
    if (startDate < LocalDateTime.now())
        return 5
    return 0
}