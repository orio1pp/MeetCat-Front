package com.pes.meetcatui.feature_event.presentation

import com.pes.meetcatui.feature_event.domain.Event

data class EventScreenState(
    val data: Event? = null,
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val errorMessage: String? = null
)