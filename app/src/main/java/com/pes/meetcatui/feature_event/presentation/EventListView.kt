package com.pes.meetcatui.feature_event.presentation

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.pes.meetcatui.feature_event.TimeFormatter
import com.pes.meetcatui.feature_event.domain.Event
import com.pes.meetcatui.ui.theme.*
import kotlin.math.roundToInt

const val EventListScreenDestination = "EventList"

@Composable
fun EventListScreen(
    viewModel: EventListViewModel,
    navtoEvent: () -> Unit,
) {
    val eventList by viewModel.eventList.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Background,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            FiltersSelection()

            EventListScreen(viewModel, eventList = eventList, navtoEvent)

            Row {
                Text(text = "MENU SELECCIÓN DE MENU DE MENU")
            }
        }
    }
}

@Composable
fun EventView(
    id: Int,
    name: String,
    desc: String,
    date: String,
    location: String,
    navtoEvent: () -> Unit,
    viewModel: EventListViewModel,
) {
    Column(
        modifier = Modifier
            /*.border(
                BorderStroke(
                    width = 1.dp,
                    color = Gray,
                )
            )*/
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(vertical = 2.dp),
        ) {
            NameButton(viewModel, id, navtoEvent, name)
        }
        Row {

            EventData(desc, date, location)

        }
        Row(
            Modifier
                .padding(horizontal = 8.dp)
                .padding(top = 12.dp)
        ) {
            Divider(
                color = LightGray, modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun NameButton(
    viewModel: EventListViewModel, id: Int, navtoEvent: () -> Unit, name: String
) {
    TextButton(
        onClick = {
            viewModel.getEvent(id = id)
            navtoEvent()
        },
    ) {
        Text(
            text = name,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun EventData(desc: String, date: String, location: String) {
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

@Preview
@Composable
fun Test() {
    //Screen(getViewModel())
}


@Composable
private fun EventListScreen(
    viewModel: EventListViewModel,
    eventList: List<Event>,
    navtoEvent: () -> Unit,
) {
    Column(
        modifier = Modifier
            .height(620.dp)
            .verticalScroll(state = ScrollState(0))
    ) {
        for (event in eventList) {

            val startDateComplete = TimeFormatter().strLocalDateTime_to_DateTime(event.startDate)

            EventView(
                id = event.eventId,
                name = event.name,
                desc = event.description,
                date = startDateComplete,
                location = event.address,
                navtoEvent = navtoEvent,
                viewModel = viewModel,
            )
        }
    }
}

