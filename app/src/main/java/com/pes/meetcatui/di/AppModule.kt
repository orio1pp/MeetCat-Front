package com.pes.meetcatui.di

import com.pes.meetcatui.feature_chat.presentation.ChatListViewModel
import com.pes.meetcatui.feature_chat.presentation.ChatViewModel
import com.pes.meetcatui.feature_event.presentation.EventViewModel
import com.pes.meetcatui.feature_event.presentation.EventListViewModel
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.CoroutineScope
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val appModule = module {
    single { CoroutineScope(SupervisorJob()) }
    viewModel {
        EventViewModel(
            dataRepository = get(),
        )
    }
    viewModel {
        EventListViewModel(
            dataRepository = get(),
        )
    }

    viewModel {
        ChatViewModel(
            //dataRepository = get(),
        )
    }
    viewModel {
        ChatListViewModel(
            dataRepository = get(),
        )
    }
}