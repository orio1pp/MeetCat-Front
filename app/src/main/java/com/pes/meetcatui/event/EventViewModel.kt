package com.pes.meetcatui.event

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pes.meetcatui.domain.DataRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import okhttp3.internal.wait
import org.koin.androidx.viewmodel.scope.emptyState

@OptIn(ExperimentalCoroutinesApi::class)
class EventViewModel(
    private val dataRepository: DataRepository,
) : ViewModel() {
    val event = dataRepository
        .getEvent(4)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)
}