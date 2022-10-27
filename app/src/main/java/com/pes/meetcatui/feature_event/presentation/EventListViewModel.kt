package com.pes.meetcatui.feature_event.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pes.meetcatui.feature_event.Resource
import com.pes.meetcatui.feature_event.domain.DataRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EventListViewModel(
    dataRepository: DataRepository,
) : ViewModel() {
    val dataRepository = dataRepository

    var id = 1
    val _event = mutableStateOf(EventScreenState())

    val eventList = dataRepository.getEventList().mapLatest { events ->
        events.asSequence().sortedBy { it.eventId }.toList()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun getEvent(id: Int) {
        _event.value = EventScreenState(
            data = eventList.value.get(id - 1),
        )
        println("break")
    }

}