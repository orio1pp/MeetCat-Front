package com.pes.meetcatui.feature_event.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.sharp.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pes.meetcatui.ui.theme.typo

@Composable
fun searchBar(eventListViewModel: EventListViewModel, modifier: Modifier) {
    var text: String by remember { mutableStateOf("") }
    Row (
        modifier = modifier.height(65.dp).width(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.Center
    ) {
        TextField(
            value = text,
            onValueChange = { newText ->
                if (newText.last() == '\n')
                    eventListViewModel.search(text)
                else
                    text = newText;
            },
            textStyle = typo.h4,
            label = {
                Text("Search")
            },
        )
        Button(
            onClick = {
                eventListViewModel.search(text)
            },
            modifier = Modifier.fillMaxHeight()
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}

@Preview
@Composable
fun searchBarPreview() {
    var text: String by remember { mutableStateOf("") }
    Row (modifier = Modifier.height(65.dp)) {
        TextField(
            value = text,
            onValueChange = { newText ->
                text = newText;
            },
            textStyle = typo.h4,
            label = {
                Text("Search")
            },
        )
        Button(
            onClick = {
                println(text)
            },
            modifier = Modifier.fillMaxHeight()
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}