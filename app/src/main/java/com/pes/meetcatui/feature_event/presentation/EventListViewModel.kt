package com.pes.meetcatui.feature_event.presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.pes.meetcatui.common.Resource
import com.pes.meetcatui.feature_event.domain.DataRepository
import com.pes.meetcatui.feature_event.domain.Event
import com.pes.meetcatui.feature_event.presentation.admin_only.ReportedListViewModel
import com.pes.meetcatui.feature_event.presentation.user_events.EventListViewModelAttending
import com.pes.meetcatui.feature_event.presentation.user_events.EventListViewModelOwn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

open class EventListViewModel(
    override val dataRepository: DataRepository,
) : EventViewModel(dataRepository) {

    protected val page = mutableStateOf(0)
    protected val titleSearch : MutableState<String?> = mutableStateOf(null)

    init {
        if (this !is ReportedListViewModel && this !is EventListViewModelAttending && this !is EventListViewModelOwn) {
            viewModelScope.launch {
                initSuper()
                setData()
            }
        }
    }

    override suspend fun setData() {
        page.value = 0
        dataRepository.getEvents(0, titleSearch.value).collect { resource ->
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

    open fun loadMore() {
        if (events.value.data != null && events.value.data!!.size != 0 && page.value > 0) {
            viewModelScope.launch {
                dataRepository.getEvents(page.value, titleSearch.value).collect { resource ->
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

    open fun search(text: String?) {
        viewModelScope.launch {
            dataRepository.getEvents(0, text).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        events.value = EventScreenState(
                            data = resource.data?.events as MutableList<Event>,
                        )
                        page.value = 1
                        titleSearch.value = text.orEmpty()
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
