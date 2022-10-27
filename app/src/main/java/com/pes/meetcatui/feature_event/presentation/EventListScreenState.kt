package com.pes.meetcatui.feature_event.presentation

import com.pes.meetcatui.feature_event.domain.Event

data class EventListScreenState(
    val data: List<Event>? = null,
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val errorMessage: String? = null
)