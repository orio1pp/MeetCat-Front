package com.pes.meetcatui.feature_event.presentation.user_events

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pes.meetcatui.R
import com.pes.meetcatui.common.BackButton
import com.pes.meetcatui.feature_event.domain.Event
import com.pes.meetcatui.feature_event.presentation.*
import com.pes.meetcatui.ui.theme.LightGray
import com.pes.meetcatui.ui.theme.typo
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun EventListScreenOwn(
    viewModel: EventListViewModelOwn,
    globalEvent: MutableState<Event?>,
    navToEditEvent: () -> Unit,
    navToMap: () -> Unit,
) {
    val eventList by viewModel.events
    val attendance by viewModel.attendance

    val searchText = remember { mutableStateOf("") }

    if (eventList.data != null
        && !eventList.isLoading
        && !eventList.hasError
        && eventList.isDetailsSelected
    ) {

        EventDetailsScreenO(
            event = eventList.eventDetailsSelected!!,
            onClick = { viewModel.setNotSelected() },
            attendanceState = attendance,
            getIsUsers = { viewModel.getIsUsers(eventList.eventDetailsSelected!!) },
            onClickJoin = {
                viewModel.addAttendance(eventList.eventDetailsSelected!!.eventId)
            },
            onClickLeave = {
                viewModel.deleteAttendance(eventList.eventDetailsSelected!!.eventId)
            },
            reportEvent = { viewModel.reportEvent(eventList.eventDetailsSelected!!) },
            deleteEvent = {
                viewModel.deleteEvent(eventList.eventDetailsSelected!!.eventId)
            },
            globalEvent = globalEvent,
            navToEditEvent = navToEditEvent,

            )
        BackHandlerO { viewModel.setNotSelected() }
    } else {
        EventListScreenContentO(
            viewModel = viewModel,
            eventList = eventList,
            navToMap = navToMap,
            searchText = searchText,
            onEventClick = { event ->
                viewModel.setSelectedEvent(event)
                searchText.value = ""
            },
        )
    }
}

@Composable
fun EventListScreenContentO(
    viewModel: EventListViewModelOwn,
    eventList: EventScreenState,
    navToMap: () -> Unit,
    onEventClick: (event: Event) -> Unit,
    searchText: MutableState<String>,
) {
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
                    EventListO(
                        eventList = eventList.data,
                        onEventClick = onEventClick,
                        onLoadMore = { viewModel.loadMore() },
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@Composable
fun EventDetailsScreenO(
    event: Event,
    onClick: () -> Unit,
    attendanceState: EventAttendanceState,
    getIsUsers: () -> Boolean,
    onClickJoin: () -> Unit,
    onClickLeave: () -> Unit,
    reportEvent: (Event) -> Unit,
    deleteEvent: () -> Unit,
    globalEvent: MutableState<Event?>,
    navToEditEvent: () -> Unit,
) {
    val openDialog = remember { mutableStateOf(false) }

    Column {
        Row {
            BackButton(function = onClick)
            Spacer(modifier = Modifier.width(235.dp))
        }
        Surface() {
            EventDetails(
                event = event,
                attendance = attendanceState,
                getIsUsers = getIsUsers,
                onClickJoin = onClickJoin,
                onClickLeave = onClickLeave,
                deleteEvent = deleteEvent,
                navToEdit = navToEditEvent,
                globalEvent = globalEvent,
            )
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
fun EventViewO(
    event: Event,
    onEventClick: (event: Event) -> Unit,
    viewModel: EventListViewModelAttending
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


@Composable
private fun EventListO(
    eventList: List<Event>,
    onEventClick: (event: Event) -> Unit,
    onLoadMore: () -> Unit,
    viewModel: EventListViewModelOwn
) {
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxHeight()
    ) {
        items(eventList) { event ->
            EventView(
                event = event,
                onEventClick = onEventClick,
                viewModel = viewModel
            )
        }
    }
    InfiniteListHandlerO(listState = listState) {
        onLoadMore()
    }
}

@Composable
fun InfiniteListHandlerO(
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
fun BackHandlerO(enabled: Boolean = true, onBack: () -> Unit) {
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
