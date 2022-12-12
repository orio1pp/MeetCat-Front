package com.pes.meetcatui.feature_event.presentation.admin_only

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.pes.meetcatui.feature_event.presentation.BackHandler
import com.pes.meetcatui.feature_event.presentation.EventDetailsScreen
import com.pes.meetcatui.feature_event.presentation.EventListScreenContent

@Composable
fun ReportedEventsListScreen(
    viewModel: ReportedListViewModel,
    navToMap: () -> Unit,
    navToCreateEvent: () -> Unit,
) {
    val eventList by viewModel.eventList

    if (eventList != null
        && eventList.data != null
        && !eventList.isLoading
        && !eventList.hasError
        && eventList.isDetailsSelected) {

        EventDetailsScreen(event = eventList.eventDetailsSelected!!, reportEvent = {viewModel.reportEvent(eventList.eventDetailsSelected!!)}) {
            viewModel.setIsSelected()
        }
        BackHandler { viewModel.setIsSelected() }
    } else {
        EventListScreenContent(
            viewModel = viewModel,
            eventList = eventList,
            navToMap = navToMap,
            navToCreateEvent = navToCreateEvent
        ) { event ->
            viewModel.setSelectedEvent(event)
        }
    }
}