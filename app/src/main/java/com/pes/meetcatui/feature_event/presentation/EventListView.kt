package com.pes.meetcatui.feature_event.presentation

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pes.meetcatui.R
import com.pes.meetcatui.common.BackButton
import com.pes.meetcatui.common.SpaceDp
import com.pes.meetcatui.feature_event.domain.Event
import com.pes.meetcatui.ui.theme.Background
import com.pes.meetcatui.ui.theme.LightGray
import com.pes.meetcatui.ui.theme.typo
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun EventListScreen(
    viewModel: EventListViewModel,
    navToMap: () -> Unit,
) {
    val eventList by viewModel.events
    val attendance by viewModel.attendance

    if (eventList != null
        && eventList.data != null
        && !eventList.isLoading
        && !eventList.hasError
        && eventList.isDetailsSelected) {

        EventDetailsScreen(
            event = eventList.eventDetailsSelected!!,
            onClick = { viewModel.setIsSelected() },
            attendanceState = attendance,
            getIsUsers = { viewModel.getIsUsers(eventList.eventDetailsSelected!!) },
            onClickJoin = {
                viewModel.addAttendance(eventList.eventDetailsSelected!!.eventId)
            },
            onClickLeave = {
                viewModel.deleteAttendance(eventList.eventDetailsSelected!!.eventId)
            },
            reportEvent = {viewModel.reportEvent(eventList.eventDetailsSelected!!)},
        )
        BackHandler { viewModel.setIsSelected() }
    } else {
        EventListScreenContent(
            viewModel = viewModel,
            eventList = eventList,
            navToMap = navToMap,
        ) { event ->
            viewModel.setSelectedEvent(event)
        }
    }
}

@Composable
fun EventListScreenContent(
    viewModel: EventListViewModel,
    eventList: EventScreenState,
    navToMap: () -> Unit,
    onEventClick: (event: Event) -> Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            navToMap.switchViewButton(
                icon = Icons.Filled.LocationOn,
            )
        },
        floatingActionButtonPosition = FabPosition.End,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            searchBar(
                viewModel,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            filtersSelection(mutableStateOf(1))
            if (eventList.isLoading) {
                Column(
                    modifier = Modifier.fillMaxSize(),
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
                    modifier = Modifier.fillMaxSize(),
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
                if (!eventList.isDetailsSelected) {
                    EventList(
                        viewModel,
                        eventList = eventList.data,
                        onEventClick = onEventClick,
                        onLoadMore = { viewModel.loadMore() },
                    )
                }
            }
        }
    }
}

@Composable
fun EventDetailsScreen(
    event: Event,
    onClick: () -> Unit,
    attendanceState: EventAttendanceState,
    getIsUsers: () -> Boolean,
    onClickJoin: () -> Unit,
    onClickLeave: ()-> Unit,
    reportEvent: (Event) -> Unit,
)
{
    val openDialog = remember { mutableStateOf(false)  }

    Column {
        Row {
            BackButton(function = onClick)
            Spacer(modifier = Modifier.width(235.dp))
            ReportButton(function = {
                openDialog.value = true
             })
        }
        Surface() {
            EventDetails(event = event, attendance = attendanceState, getIsUsers, onClickJoin = onClickJoin, onClickLeave = onClickLeave)
        }
    }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = stringResource(R.string.areYouSureToReportMsg))
            },
            confirmButton = {
                Button(
                    onClick = {
                        reportEvent(event)
                        openDialog.value = false
                    }) {
                    Text(stringResource(R.string.confirm))
                }
            },
        )
    }

}

@Composable
fun ReportButton(function: () -> Unit = {}) {
    FloatingActionButton(
        onClick = function,
        modifier = Modifier
            .alpha(1.0f)
            .padding(start = 16.dp, top = 16.dp)
            .clip(CircleShape)
            .border(2.dp, Color(0xFF838383), shape = CircleShape),
        backgroundColor = Color(0xFFBEBEBE),
        elevation = FloatingActionButtonDefaults.elevation(2.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Warning,
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            tint = Color(0xFF5A5A5A),
        )
    }
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
            NameButton(event, onEventClick)
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
    event: Event, onEventClick: (event: Event) -> Unit
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
        modifier = Modifier.fillMaxHeight()
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
fun BackHandler(enabled: Boolean = true, onBack: () -> Unit) {
    val currentOnBack by rememberUpdatedState(onBack)

    val backCallback = remember {
        object : OnBackPressedCallback(enabled) {
            override fun handleOnBackPressed() {
                currentOnBack()
            }
        }
    }

    SideEffect {
        backCallback.isEnabled = enabled
    }

    val backDispatcher = checkNotNull(LocalOnBackPressedDispatcherOwner.current) {
        "No OnBackPressedDispatcherOwner was provided via LocalOnBackPressedDispatcherOwner"
    }.onBackPressedDispatcher

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner, backDispatcher) {
        backDispatcher.addCallback(lifecycleOwner, backCallback)
        onDispose {
            backCallback.remove()
        }
    }
}
