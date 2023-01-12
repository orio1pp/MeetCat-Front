package com.pes.meetcatui.feature_event.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pes.meetcatui.common.Resource
import com.pes.meetcatui.feature_event.domain.DataRepository
import com.pes.meetcatui.feature_event.domain.Event
import kotlinx.coroutines.launch

abstract class EventViewModel (
    open val dataRepository: DataRepository,
) : ViewModel() {
    val username = mutableStateOf("")
    val attendance = mutableStateOf(EventAttendanceState())
    val events = mutableStateOf(EventScreenState())

    abstract suspend fun setData()

    fun initSuper() {
        viewModelScope.launch {
            dataRepository.getUsername()
                .collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            username.value = resource.data!!
                        }
                    }
                }
        }
    }

    fun deleteEvent(eventId: Long) {
        viewModelScope.launch {
            dataRepository.deleteEvent(eventId).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        events.value = EventScreenState(
                            isLoading = true,
                        )
                    }
                    is Resource.Error -> {
                        events.value = EventScreenState(
                            hasError = true,
                            errorMessage = resource.message
                        )
                    }
                    is Resource.Loading -> {
                        events.value = EventScreenState(
                            isLoading = true
                        )
                    }
                }
            }
            setData()
        }
        setNotSelected()
    }

    fun getIsUsers(event: Event) : Boolean {
        return username.value == event.username
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

    fun reportEvent(event: Event) {
        viewModelScope.launch {
            dataRepository.reportEvent(event)
        }
    }

    fun setSelectedEvent(event: Event) {
        events.value = EventScreenState(
            isDetailsSelected = true,
            eventDetailsSelected = event,
            data = events.value.data
        )

        viewModelScope.launch {
            dataRepository.getAttendance(event.eventId).collect { resource ->
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

    fun setNotSelected() {
        events.value = EventScreenState(
            isDetailsSelected = false,
            data = events.value.data
        )
    }
}