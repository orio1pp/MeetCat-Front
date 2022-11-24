package com.pes.meetcatui.feature_event.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pes.meetcatui.common.BackButton
import com.pes.meetcatui.common.ContinuousSlider
import com.pes.meetcatui.feature_event.domain.Event
import com.pes.meetcatui.ui.theme.Background
import com.pes.meetcatui.ui.theme.LightGray
import com.pes.meetcatui.ui.theme.typo
import kotlinx.coroutines.flow.distinctUntilChanged

const val EventListScreenDestination = "EventList"

@Composable
fun EventListScreen(
    viewModel: EventListViewModel,
    navToMap: () -> Unit,
    navToCreateEvent: () -> Unit,
) {
    val eventList by viewModel.eventList

    if (eventList.isLoading) {
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
    } else if (eventList.hasError) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Error!",
                style = typo.h2,
                color = Color(0xFFA00000)
            )
        }
    } else if (eventList.data != null) {
        if (eventList.isDetailsSelected) {
            EventDetailsScreen(event = eventList.eventDetailsSelected!!) {
                viewModel.setIsSelected()
            }
        } else {
            EventListScreenContent(
                viewModel = viewModel,
                eventList = eventList.data!!,
                navToMap = navToMap,
                navToCreateEvent = navToCreateEvent
            ) { event ->
                viewModel.setSelectedEvent(event)
            }
        }
    }
}

@Composable
fun InfiniteListHandler(
    listState: LazyListState,
    buffer: Int = 2,
    onLoadMore: () -> Unit
) {
    val loadMore = remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItemsNumber = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1

            lastVisibleItemIndex > (totalItemsNumber - buffer)
        }
    }

    LaunchedEffect(loadMore) {
        snapshotFlow { loadMore.value }
            .distinctUntilChanged()
            .collect {
                onLoadMore()
            }
    }
}

@Composable
fun EventListScreenContent(viewModel: EventListViewModel, eventList: List<Event>, navToMap: () -> Unit, navToCreateEvent: () -> Unit, onEventClick: (event: Event) -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Background,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            FiltersSelection()

            EventList(
                viewModel,
                eventList = eventList!!,
                { }, //navtoEvent
                onLoadMore = { viewModel.loadMore() },
            )
            Row {
                navToMap.switchViewButton(
                    icon = Icons.Filled.LocationOn,
                )
                navToCreateEvent.switchViewButton(
                    icon = Icons.Filled.Create,
                )
            }
        }
    }
}

@Composable
fun EventDetailsScreen(
    event: Event,
    onClick: () -> Unit
)
{
    EventDetails(event = event)
    BackButton(function = onClick)
}

@Composable
fun EventView(
    event: Event,
    viewModel: EventListViewModel,
    onEventClick: (event: Event) -> Unit,
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
            NameButton(viewModel, event, onEventClick)
        }
        Row {
            EventData(
                event.description,
                event.endDate,
                event.address ?: ""
            )
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
    viewModel: EventListViewModel, event: Event, onEventClick: (event: Event) -> Unit
) {
    TextButton(
        onClick = { onEventClick(event) },
    ) {
        Text(
            text = event.name,
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
private fun EventData(desc: String?, date: String?, location: String) {
    Column(
        modifier = Modifier.width(192.dp)
    ) {
        Text(
            text = desc ?: "",
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
            text = date ?: "",
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
private fun EventList(
    viewModel: EventListViewModel,
    eventList: List<Event>,
    onEventClick: (event: Event) -> Unit,
    onLoadMore: () -> Unit,
) {
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        modifier = Modifier
            .height(590.dp)
    ) {
        items(eventList) { event ->
            EventView(
                event = event,
                viewModel = viewModel,
                onEventClick = onEventClick
            )
        }
    }
    InfiniteListHandler(listState = listState) {
        onLoadMore()
    }
}

@Composable
fun switchViewButtonmap(function: () -> Unit = {}) {
    IconButton(
        onClick = function,
        modifier = Modifier
            .alpha(1.0f)
            .padding(start = 16.dp, top = 16.dp)
            .clip(CircleShape)
            .border(1.dp, Color(0xFFA0A0A0), shape = CircleShape)
            .background(color = Color(color = 0xFFF4F4F4)),

        ) {
        Icon(
            imageVector = Icons.Filled.LocationOn,
            contentDescription = null,
            modifier = Modifier.size(32.dp),
            tint = Color(0xFF0E0E0E),
        )
    }
}