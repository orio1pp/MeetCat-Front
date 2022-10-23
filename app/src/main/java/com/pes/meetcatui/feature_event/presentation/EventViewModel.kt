package com.pes.meetcatui.feature_event.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pes.meetcatui.Resource
import com.pes.meetcatui.feature_event.domain.DataRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EventViewModel(
    dataRepository: DataRepository,
) : ViewModel() {
    //lateinit var _event: EventScreenState

    init {
        viewModelScope.launch {
            dataRepository.getEvent(5).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _event = EventScreenState(
                            data = resource.data
                        )
                    }
                    is Resource.Error -> {
                        _event = EventScreenState(
                            hasError = true,
                            errorMessage = resource.message
                        )
                    }
                    is Resource.Loading -> {
                        _event = EventScreenState(
                            isLoading = true
                        )
                    }
                }
            }
        }
    }
    /*
    val eventResource = dataRepository
        .getEvent(5)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)*/
}