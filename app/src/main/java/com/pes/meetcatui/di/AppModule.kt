package com.pes.meetcatui.di

import com.pes.meetcatui.feature_event.presentation.CreateEventViewModel
import com.pes.meetcatui.feature_event.presentation.EventListViewModel
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.CoroutineScope
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val appModule = module {
    single { CoroutineScope(SupervisorJob()) }
    viewModel {
        EventListViewModel(
            dataRepository = get(),
        )
    }
    viewModel {
        CreateEventViewModel(
            dataRepository = get(),
        )
    }
}