package com.pes.meetcatui.feature_event.presentation.admin_only

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pes.meetcatui.feature_event.Resource
import com.pes.meetcatui.feature_event.domain.DataRepository
import com.pes.meetcatui.feature_event.domain.Event
import com.pes.meetcatui.feature_event.presentation.EventListScreenState
import com.pes.meetcatui.feature_event.presentation.EventListViewModel
import kotlinx.coroutines.launch

class ReportedListViewModel (override val dataRepository: DataRepository) : EventListViewModel() {
    init {
        viewModelScope.launch {
            dataRepository.getReportedEvents(0, titleSearch).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        eventList.value = EventListScreenState(
                            data = resource.data?.events as MutableList<Event>,
                            page = 1
                        )
                    }
                    is Resource.Error -> {
                        eventList.value = EventListScreenState(
                            hasError = true,
                            errorMessage = resource.message
                        )
                    }
                    is Resource.Loading -> {
                        eventList.value = EventListScreenState(
                            isLoading = true
                        )
                    }
                }
            }
        }
    }

    fun setSelectedEvent(event: Event) {
        eventList.value = EventListScreenState(
            isDetailsSelected = true,
            eventDetailsSelected = event,
            data = eventList.value.data
        )
    }

    fun setIsSelected() {
        eventList.value = EventListScreenState(
            isDetailsSelected = false,
            data = eventList.value.data
        )
    }

    fun loadMore() {
        if (eventList.value.data != null && eventList.value.data!!.size != 0 && eventList.value.page > 0) {
            viewModelScope.launch {
                dataRepository.getReportedEvents(eventList.value.page, titleSearch).collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            eventList.value.data!!.addAll(resource.data!!.events.toMutableList())
                            eventList.value = EventListScreenState(
                                data = eventList.value.data,
                                page = eventList.value.page + 1
                            )
                        }
                        is Resource.Error -> {
                            eventList.value = EventListScreenState(
                                hasError = true,
                                errorMessage = resource.message
                            )
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    fun search(text: String) {
        titleSearch = text
        viewModelScope.launch {
            dataRepository.getReportedEvents(0, titleSearch).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        eventList.value = EventListScreenState(
                            data = resource.data?.events as MutableList<Event>,
                            page = 1
                        )
                    }
                    is Resource.Error -> {
                        eventList.value = EventListScreenState(
                            hasError = true,
                            errorMessage = resource.message
                        )
                    }
                    is Resource.Loading -> {
                        eventList.value = EventListScreenState(
                            isLoading = true
                        )
                    }
                }
            }
        }
    }
}