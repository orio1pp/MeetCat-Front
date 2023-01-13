package com.pes.meetcatui.feature_event.presentation.user_events

import androidx.lifecycle.viewModelScope
import com.pes.meetcatui.common.Resource
import com.pes.meetcatui.feature_event.domain.DataRepository
import com.pes.meetcatui.feature_event.domain.Event
import com.pes.meetcatui.feature_event.presentation.EventListViewModel
import com.pes.meetcatui.feature_event.presentation.EventScreenState
import kotlinx.coroutines.launch

open class EventListViewModelAttending(
    override val dataRepository: DataRepository,
) : EventListViewModel(dataRepository) {

    init {
        viewModelScope.launch {
            setData()
        }
    }

    override suspend fun setData() {
        dataRepository.getAttendedEvents().collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    events.value = EventScreenState(
                        data = resource.data?.events as MutableList<Event>,
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
    }
}
