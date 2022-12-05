package com.pes.meetcatui.feature_event.presentation

import android.webkit.URLUtil
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pes.meetcatui.R
import com.pes.meetcatui.ui.theme.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeParseException

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

private fun checkInput(name: String, subtitle: String, description: String,
                       startDate: String, endDate: String, location:String, place: String,
                       address: String, link: String) : Int{
    if (!(name != "" && subtitle != "" && description != "" && startDate != "" && endDate != "" && location != "" && place != "" && address != "" && link != ""))
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
    try {
        LocalDateTime.parse(startDate)
        LocalDateTime.parse(endDate)
        return 0
    } catch (e: DateTimeParseException) {
        return 3//stringResource(R.string.createEventErrorDateIsNotDate);
    }
}

@Composable
fun CreateEventView(
    viewModel: CreateEventViewModel,
    navToEvents: () -> Unit,
) {
    var name: String by remember { mutableStateOf("") }
    var subtitle: String by remember { mutableStateOf("") }
    var description: String by remember { mutableStateOf("") }
    var startDate: String by remember { mutableStateOf("") }
    var endDate: String by remember { mutableStateOf("") }
    var location: String by remember { mutableStateOf("") }
    var place: String by remember { mutableStateOf("") }
    var address: String by remember { mutableStateOf("") }
    var link: String by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Background,
    ) {
        Column(
            Modifier
                .padding(vertical = 16.dp)
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

                startDate = TextFieldLabeled(startDate, labelText)
            }
            Row(Modifier.padding(vertical = 8.dp)) {
                var labelText = stringResource(R.string.to)

                endDate = TextFieldLabeled(endDate, labelText)
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
                    name,
                    subtitle,
                    description,
                    startDate,
                    endDate,
                    location,
                    place,
                    address,
                    link,
                    navToEvents
                )
            }
        }
    }
}

@Composable
private fun CreateButton(
    viewModel: CreateEventViewModel,
    name: String,
    subtitle: String,
    description: String,
    startDate: String,
    endDate: String,
    location: String,
    place: String,
    address: String,
    link: String,
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
            errorString = stringResource(R.string.createEventErrorDateIsNotDate);
        else if (errorStringId == 4)
            errorString = stringResource(R.string.createEventErrorLocationIsNotCorrectFormat);
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
                    viewModel.createEvent(
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
                    navToEvents();
                }
            },
            Modifier.padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = backgroundColor,
                contentColor = focusedLabelColor
            )
        ) {
            Text("Create")
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
