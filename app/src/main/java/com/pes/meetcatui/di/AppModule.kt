package com.pes.meetcatui.di

import com.pes.meetcatui.event.EventViewModel
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.CoroutineScope
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.Scope

val appModule = module {
    single { CoroutineScope(SupervisorJob()) }
    viewModel {
        EventViewModel(
            dataRepository = get(),
        )
    }
}