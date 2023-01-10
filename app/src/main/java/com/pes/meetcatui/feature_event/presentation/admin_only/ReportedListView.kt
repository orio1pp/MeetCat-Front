package com.pes.meetcatui.feature_event.presentation.admin_only

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.pes.meetcatui.feature_event.presentation.BackHandler
import com.pes.meetcatui.feature_event.presentation.EventDetailsScreen
import com.pes.meetcatui.feature_event.presentation.EventListScreenContent

@Composable
fun ReportedEventsListScreen(
    viewModel: ReportedListViewModel,
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
            onClick = { viewModel.setNotSelected() },
            attendanceState = attendance,
            getIsUsers = { false },
            onClickJoin = {
                viewModel.addAttendance(eventList.eventDetailsSelected!!.eventId)
            },
            onClickLeave = {
                viewModel.deleteAttendance(eventList.eventDetailsSelected!!.eventId)
            },
            reportEvent = {viewModel.reportEvent(eventList.eventDetailsSelected!!)},
            deleteEvent = {},
            globalEvent = mutableStateOf(null),
            navToEditEvent = {},
        )
        BackHandler { viewModel.setNotSelected() }
    } else {
        EventListScreenContent(
            viewModel = viewModel,
            eventList = eventList,
            navToMap = navToMap,
            searchText = mutableStateOf(""),
            onEventClick = { event ->
                viewModel.setSelectedEvent(event)
            }
        )
    }
}