package com.pes.meetcatui.feature_event.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pes.meetcatui.feature_event.Resource
import com.pes.meetcatui.feature_event.domain.DataRepository
import com.pes.meetcatui.feature_event.domain.Event
import kotlinx.coroutines.launch

class EventListViewModel(
    val dataRepository: DataRepository,
    var hasLiked: Boolean = false,
    var hasDisLiked: Boolean = false
) : ViewModel() {

    val eventList = mutableStateOf(EventListScreenState())

    init {
        viewModelScope.launch {
            dataRepository.getEvents(0).collect { resource ->
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
                dataRepository.getEvents(eventList.value.page).collect { resource ->
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

    fun likeEvent(eventId: Long): Boolean {
        viewModelScope.launch { dataRepository.likeEvent(eventId) }
        hasLiked = true
        hasDisLiked = false
        return true
    }

    fun dislikeEvent(eventId: Long): Boolean {
        viewModelScope.launch { dataRepository.dislikeEvent(eventId) }
        hasDisLiked = true
        hasLiked = false
        return true
    }

    fun handleVote(vote: String, eventId: Long) {
        when (vote) {
            "like" -> {
                likeEvent(eventId)
            }
            "dislike" -> {
                dislikeEvent(eventId)
            }
        }
    }
}