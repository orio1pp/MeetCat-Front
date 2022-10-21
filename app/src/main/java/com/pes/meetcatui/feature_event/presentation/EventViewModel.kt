package com.pes.meetcatui.feature_event.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pes.meetcatui.feature_event.domain.DataRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class EventViewModel(
    dataRepository: DataRepository,
) : ViewModel() {
    val event = dataRepository
        .getEvent(5)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)
}