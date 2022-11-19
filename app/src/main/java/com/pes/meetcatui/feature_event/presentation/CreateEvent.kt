package com.pes.meetcatui.feature_event.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
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
fun CreateEventView () {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Background,
    ) {
        Column (
            Modifier.padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(Modifier.padding(vertical = 8.dp)) {
                var previewText = "";
                var labelText = "Name"

                TextFieldLabeled(previewText, labelText)
            }
            Row(Modifier.padding(vertical = 8.dp)) {
                var previewText = "";
                var labelText = "Subtitle"

                TextFieldLabeled(previewText, labelText)
            }
            Row(Modifier.padding(vertical = 8.dp)) {
                var previewText = "";
                var labelText = "Description"

                TextFieldLabeled(previewText, labelText)
            }
            Row(Modifier.padding(vertical = 8.dp)) {
                var previewText = "";
                var labelText = "Start date"

                TextFieldLabeled(previewText, labelText)
            }
            Row(Modifier.padding(vertical = 8.dp)) {
                var previewText = "";
                var labelText = "End date"

                TextFieldLabeled(previewText, labelText)
            }
            Row(Modifier.padding(vertical = 8.dp)) {
                var previewText = "";
                var labelText = "Place"

                TextFieldLabeled(previewText, labelText)
            }
            Row(Modifier.padding(vertical = 8.dp)) {
                var previewText = "";
                var labelText = "Address"

                TextFieldLabeled(previewText, labelText)
            }
            Row(Modifier.padding(vertical = 8.dp)) {
                var previewText = "";
                var labelText = "Link"

                TextFieldLabeled(previewText, labelText)
            }
            Row(Modifier.padding(vertical = 8.dp)) {
                Button(
                    onClick = { /*TODO*/ },
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
private fun TextFieldLabeled(previewText: String, labelText: String) {
    var eventDate: String by remember { mutableStateOf(previewText) }
    TextField(
        value = eventDate,
        onValueChange = { newText ->
            eventDate = newText
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
}