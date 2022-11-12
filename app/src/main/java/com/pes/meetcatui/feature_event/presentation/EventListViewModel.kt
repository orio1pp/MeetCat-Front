package com.pes.meetcatui.feature_event.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pes.meetcatui.feature_event.Resource
import com.pes.meetcatui.feature_event.domain.DataRepository
import com.pes.meetcatui.feature_event.domain.Event
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EventListViewModel(
    dataRepository: DataRepository,
) : ViewModel() {

    val dataRepository = dataRepository

    val _eventList = mutableStateOf(EventListScreenState())

    init {
        viewModelScope.launch {
            dataRepository.getEvents().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _eventList.value = EventListScreenState(
                            data = resource.data
                        )
                    }
                    is Resource.Error -> {
                        _eventList.value = EventListScreenState(
                            hasError = true,
                            errorMessage = resource.message
                        )
                    }
                    is Resource.Loading -> {
                        _eventList.value = EventListScreenState(
                            isLoading = true
                        )
                    }
                }
            }
        }
    }

    fun setSelectedEvent(event: Event) {
        _eventList.value = EventListScreenState(
            isDetailsSelected = true,
            eventDetailsSelected = event,
            data = _eventList.value.data
        )
    }

    fun setIsSelected() {
        _eventList.value = EventListScreenState(
            isDetailsSelected = false,
            data = _eventList.value.data
        )
    }
}