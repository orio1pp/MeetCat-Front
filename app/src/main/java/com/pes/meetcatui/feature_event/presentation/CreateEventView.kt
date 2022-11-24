package com.pes.meetcatui.feature_event.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pes.meetcatui.ui.theme.*

const val CreateEventDestination = "CreateEvent"
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

val backgroundColor = Color(0xFFF5DDE0)
val focusedLabelColor = Color(0xFF00FFFF)
val unfocusedLabelColor = Color(0xFFED3419)

@Composable
fun CreateEventView(
    viewModel: CreateEventViewModel
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
            Modifier.padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(Modifier.padding(vertical = 8.dp)) {
                var labelText = "Name"

                name = TextFieldLabeled(name, labelText)
            }
            Row(Modifier.padding(vertical = 8.dp)) {
                var labelText = "Subtitle"

                subtitle = TextFieldLabeled(subtitle, labelText)
            }
            Row(Modifier.padding(vertical = 8.dp)) {
                var labelText = "Description"

                description = TextFieldLabeled(description, labelText)
            }
            Row(Modifier.padding(vertical = 8.dp)) {
                var labelText = "Start date"

                startDate = TextFieldLabeled(startDate, labelText)
            }
            Row(Modifier.padding(vertical = 8.dp)) {
                var labelText = "End date"

                endDate = TextFieldLabeled(endDate, labelText)
            }
            Row(Modifier.padding(vertical = 8.dp)) {
                var labelText = "Location"

                location = TextFieldLabeled(location, labelText)
            }
            Row(Modifier.padding(vertical = 8.dp)) {
                var labelText = "Place"

                place = TextFieldLabeled(place, labelText)
            }
            Row(Modifier.padding(vertical = 8.dp)) {
                var labelText = "Address"

                address = TextFieldLabeled(address, labelText)
            }
            Row(Modifier.padding(vertical = 8.dp)) {
                var labelText = "Link"

                link = TextFieldLabeled(link, labelText)
            }
            Row(Modifier.padding(vertical = 8.dp)) {
                Button(
                    onClick = {
                        println("hello")
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
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = backgroundColor,
                        contentColor = unfocusedLabelColor
                    )
                ) {
                    Text("Create")
                }
            }
        }
    }
}

@Composable
private fun TextFieldLabeled(previewText: String, labelText: String): String {
    var text: String by remember { mutableStateOf(previewText) }
    TextField(
        value = text,
        onValueChange = { newText ->
            text = newText
        },
        textStyle = typo.h4,
        label = {
            Text(labelText)
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = backgroundColor,
            focusedLabelColor = focusedLabelColor,
            unfocusedLabelColor = unfocusedLabelColor
        ),
    )
    return text;
}