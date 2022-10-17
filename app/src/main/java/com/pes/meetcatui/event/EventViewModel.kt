package com.pes.meetcatui.event

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pes.meetcatui.domain.DataRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class EventViewModel(
    dataRepository: DataRepository,
) : ViewModel() {
    val event = dataRepository
        .getEvent(4)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)
}