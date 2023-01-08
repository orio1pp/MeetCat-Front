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
                        events.value = EventListScreenState(
                            data = resource.data?.events as MutableList<Event>,
                            page = 1
                        )
                    }
                    is Resource.Error -> {
                        events.value = EventListScreenState(
                            hasError = true,
                            errorMessage = resource.message
                        )
                    }
                    is Resource.Loading -> {
                        events.value = EventListScreenState(
                            isLoading = true
                        )
                    }
                }
            }
        }
    }

    override fun loadMore() {
        if (events.value.data != null && events.value.data!!.size != 0 && events.value.page > 0) {
            viewModelScope.launch {
                dataRepository.getReportedEvents(events.value.page, titleSearch).collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            events.value.data!!.addAll(resource.data!!.events.toMutableList())
                            events.value = EventListScreenState(
                                data = events.value.data,
                                page = events.value.page + 1
                            )
                        }
                        is Resource.Error -> {
                            events.value = EventListScreenState(
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
                        events.value = EventListScreenState(
                            data = resource.data?.events as MutableList<Event>,
                            page = 1
                        )
                    }
                    is Resource.Error -> {
                        events.value = EventListScreenState(
                            hasError = true,
                            errorMessage = resource.message
                        )
                    }
                    is Resource.Loading -> {
                        events.value = EventListScreenState(
                            isLoading = true
                        )
                    }
                }
            }
        }
    }
}