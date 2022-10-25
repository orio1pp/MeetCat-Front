package com.pes.meetcatui.feature_event.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pes.meetcatui.feature_event.Resource
import com.pes.meetcatui.feature_event.domain.DataRepository
import kotlinx.coroutines.launch

class EventListViewModel(dataRepository: DataRepository,
) : ViewModel() {

    val _event = mutableStateOf(EventListScreenState())

    init {
        viewModelScope.launch {
            dataRepository.getEventList().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _event.value = EventListScreenState(
                            data = resource.data,
                        )
                    }
                    is Resource.Error -> {
                        _event.value = EventListScreenState(
                            hasError = true,
                            errorMessage = resource.message
                        )
                    }
                    is Resource.Loading -> {
                        _event.value = EventListScreenState(
                            isLoading = true
                        )
                    }
                }
            }
        }
    }
}