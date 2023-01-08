package com.pes.meetcatui.feature_event.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.pes.meetcatui.common.Resource
import com.pes.meetcatui.feature_event.domain.DataRepository
import com.pes.meetcatui.feature_event.domain.Event
import com.pes.meetcatui.feature_event.presentation.admin_only.ReportedListViewModel
import kotlinx.coroutines.launch

open class EventListViewModel(
    override val dataRepository: DataRepository,
) : EventViewModel(dataRepository) {

    protected val page = mutableStateOf(0)
    var titleSearch: String? = null

    init {
        if (this !is ReportedListViewModel) {
            viewModelScope.launch {
                initSuper()
                dataRepository.getEvents(0, titleSearch).collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            events.value = EventScreenState(
                                data = resource.data?.events as MutableList<Event>,
                            )
                            page.value = 1
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
    }

    open fun loadMore() {
        if (events.value.data != null && events.value.data!!.size != 0 && page.value > 0) {
            viewModelScope.launch {
                dataRepository.getEvents(page.value, titleSearch).collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            events.value.data!!.addAll(resource.data!!.events.toMutableList())
                            events.value = EventScreenState(
                                data = events.value.data,
                            )
                            page.value++
                        }
                        is Resource.Error -> {
                            events.value = EventScreenState(
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
                        events.value = EventScreenState(
                            data = resource.data?.events as MutableList<Event>,
                        )
                        page.value = 1
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
}
