package com.pes.meetcatui.feature_event.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pes.meetcatui.common.Resource
import com.pes.meetcatui.feature_event.domain.DataRepository
import com.pes.meetcatui.feature_event.domain.Event
import kotlinx.coroutines.launch

class EventListViewModel(
    val dataRepository: DataRepository,
) : ViewModel() {

    val eventList = mutableStateOf(EventListScreenState())
    var titleSearch: String? = null
    val attendance = mutableStateOf(EventAttendanceState())

    init {
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

    fun search(text: String) {
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

    fun isAttended(eventId: Long) {
        viewModelScope.launch {
            dataRepository.getAttendance(eventId).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        attendance.value = EventAttendanceState(
                            isAttended = resource.data!!,
                        )
                    }
                    is Resource.Error -> {
                        attendance.value = EventAttendanceState(
                            hasError = true,
                            errorMessage = resource.message
                        )
                    }
                    is Resource.Loading -> {
                        attendance.value = EventAttendanceState(
                            isLoading = true
                        )
                    }
                }
            }
        }
    }

    fun addAttendance(eventId: Long) {
        viewModelScope.launch {
            dataRepository.createAttendance(eventId).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        attendance.value = EventAttendanceState(
                            isAttended = true,
                        )
                    }
                    is Resource.Error -> {
                        attendance.value = EventAttendanceState(
                            hasError = true,
                            errorMessage = resource.message
                        )
                    }
                    is Resource.Loading -> {
                        attendance.value = EventAttendanceState(
                            isLoading = true
                        )
                    }
                }
            }
        }
    }

    fun deleteAttendance(eventId: Long) {
        viewModelScope.launch {
            dataRepository.deleteAttendance(eventId).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        attendance.value = EventAttendanceState(
                            isAttended = false,
                        )
                    }
                    is Resource.Error -> {
                        attendance.value = EventAttendanceState(
                            hasError = true,
                            errorMessage = resource.message
                        )
                    }
                    is Resource.Loading -> {
                        attendance.value = EventAttendanceState(
                            isLoading = true
                        )
                    }
                }
            }
        }
    }
}