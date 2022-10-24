package com.pes.meetcatui.feature_event.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pes.meetcatui.feature_event.Resource
import com.pes.meetcatui.feature_event.domain.DataRepository
import kotlinx.coroutines.launch

class EventViewModel(
    dataRepository: DataRepository,
) : ViewModel() {

    val _event = mutableStateOf(EventScreenState())

    init {
        viewModelScope.launch {
            dataRepository.getEvent(5).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _event.value = EventScreenState(
                            data = resource.data,
                        )
                    }
                    is Resource.Error -> {
                        _event.value = EventScreenState(
                            hasError = true,
                            errorMessage = resource.message
                        )
                    }
                    is Resource.Loading -> {
                        _event.value = EventScreenState(
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