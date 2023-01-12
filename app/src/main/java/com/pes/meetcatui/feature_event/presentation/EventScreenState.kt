package com.pes.meetcatui.feature_event.presentation

import com.pes.meetcatui.feature_event.domain.Event

data class EventScreenState(
    val data: MutableList<Event>? = null,
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val errorMessage: String? = null,
    val isDetailsSelected: Boolean = false,
    val eventDetailsSelected: Event? = null,
)
