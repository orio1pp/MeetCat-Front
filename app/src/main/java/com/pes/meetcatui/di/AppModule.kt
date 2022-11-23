package com.pes.meetcatui.di

import com.pes.meetcatui.feature_event.presentation.EventListViewModel
import com.pes.meetcatui.feature_event.presentation.MapViewModel
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
        MapViewModel(
            dataRepository = get(),
        )
    }
}