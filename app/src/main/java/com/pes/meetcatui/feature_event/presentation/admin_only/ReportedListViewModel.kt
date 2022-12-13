package com.pes.meetcatui.feature_event.presentation.admin_only

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pes.meetcatui.common.Resource
import com.pes.meetcatui.feature_event.domain.DataRepository
import com.pes.meetcatui.feature_event.domain.Event
import com.pes.meetcatui.feature_event.presentation.EventListScreenState
import com.pes.meetcatui.feature_event.presentation.EventListViewModel
import kotlinx.coroutines.launch

class ReportedListViewModel (override val dataRepository: DataRepository) : EventListViewModel(dataRepository) {
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

    override fun loadMore() {
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

    override fun search(text: String) {
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