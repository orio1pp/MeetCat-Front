package com.pes.meetcatui.feature_event.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pes.meetcatui.feature_event.Resource
import com.pes.meetcatui.feature_event.domain.DataRepository
import com.pes.meetcatui.feature_event.domain.Event
import com.pes.meetcatui.feature_event.presentation.admin_only.ReportedListViewModel
import kotlinx.coroutines.launch

open class EventListViewModel(
    open val dataRepository: DataRepository,
) : ViewModel() {

    val eventList = mutableStateOf(EventListScreenState())
    var titleSearch: String? = null

    init {
        if (this !is ReportedListViewModel) {
            viewModelScope.launch {
                dataRepository.getEvents(0, titleSearch).collect { resource ->
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

    open fun loadMore() {
        if (eventList.value.data != null && eventList.value.data!!.size != 0 && eventList.value.page > 0) {
            viewModelScope.launch {
                dataRepository.getEvents(eventList.value.page, titleSearch).collect { resource ->
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

    open fun search(text: String) {
        titleSearch = text
        viewModelScope.launch {
            dataRepository.getEvents(0, titleSearch).collect { resource ->
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

    fun reportEvent(event: Event) {
        viewModelScope.launch {
            dataRepository.reportEvent(event)
        }
    }
}