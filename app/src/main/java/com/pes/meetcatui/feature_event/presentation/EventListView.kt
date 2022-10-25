package com.pes.meetcatui.feature_event.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pes.meetcatui.common.BackButton
import com.pes.meetcatui.feature_event.domain.Event
import com.pes.meetcatui.ui.theme.*
import kotlin.math.roundToInt


@Composable
fun EventView(name: String, desc: String, date: String, location: String) {
    Column(
        modifier = Modifier
            .border(
                BorderStroke(
                    width = 1.dp,
                    color = Gray,
                )
            )
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(vertical = 2.dp),
        ) {
            Text(
                text = name,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Row {

            Column(
                modifier = Modifier.width(192.dp)
            ) {
                Text(
                    text = desc,
                    style = typo.body1,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Column(
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Divider(
                    color = LightGray, modifier = Modifier
                        .height(48.dp)
                        .width(1.dp)
                )
            }
            Column {
                Text(
                    text = date,
                    style = typo.body1,
                    color = LightGray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = location,
                    style = typo.body1,
                    color = LightGray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

        }
    }
}

@Composable
fun ContinuousSlider(start: Float, end: Float) {
    val range = start..end
    var sliderPosition by remember { mutableStateOf(start) }
    Row {
        Text(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
            text = sliderPosition.roundToInt().toString(),
            style = typo.body1,
        )
        Slider(
            value = sliderPosition,
            valueRange = range,
            onValueChange = { sliderPosition = it },
            colors = SliderDefaults.colors(
                thumbColor = Highlight,
                activeTrackColor = Highlight,
            ),
        )
    }
}

@Composable
fun FiltersSelection() {
    Row {
        Column(modifier = Modifier.width(96.dp)) {
            Text(
                modifier = Modifier.padding(vertical = 12.dp),
                text = "Distància",
                style = typo.body1,
            )
        }
        Column {
            ContinuousSlider(10f, 50f)
        }
    }
    Row {
        Column(modifier = Modifier.width(96.dp)) {
            Text(
                modifier = Modifier.padding(vertical = 12.dp),
                text = "Data",
                style = typo.body1,
            )
        }
        Column {
            ContinuousSlider(10f, 50f)
        }
    }
}

@Composable
fun Screen(
    viewModel: EventListViewModel,
) {

    val eventState = viewModel._event.value

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Background,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            FiltersSelection()


            if (eventState.isLoading) {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Loading...", style = typo.h2
                    )
                }
            } else {
                if (!eventState.hasError) {
                    BackButton()

                    val eventList = eventState.data!!

                    EventListScreen(
                        eventList = eventList,
                    )
                } else {
                    Column(
                        Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Error!", style = typo.h2, color = Color(0xFFA00000)
                        )
                    }
                }
            }
            Row {
                Text(text = "MENU SELECCIÓN DE MENU DE MENU")
            }
        }
    }
}

@Composable
private fun EventListScreen(eventList: List<Event>) {
    Column(
        modifier = Modifier
            .height(680.dp)
            .verticalScroll(state = ScrollState(0))
    ) {
        for (Event in eventList) {
            EventView(
                name = Event.name,
                desc = Event.description,
                date = Event.startDate,
                location = Event.address,
            )
        }
    }
}