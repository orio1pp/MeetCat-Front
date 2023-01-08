package com.pes.meetcatui.di

import com.pes.meetcatui.feature_chat.presentation.screen_friend_search.FriendSearchViewModel
import com.pes.meetcatui.feature_event.presentation.CreateEventViewModel
import com.pes.meetcatui.feature_event.presentation.EventListViewModel
import com.pes.meetcatui.feature_event.presentation.MapViewModel
import com.pes.meetcatui.feature_user.presentation.register_screen.RegisterViewModel
import com.pes.meetcatui.feature_event.presentation.admin_only.ReportedListViewModel
import com.pes.meetcatui.feature_user.presentation.screen_login.ProfileViewModel
import com.pes.meetcatui.feature_user.presentation.screen_normal_login.NormalLoginViewModel
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
        ReportedListViewModel(
            dataRepository = get(),
        )
    }
    viewModel {
        CreateEventViewModel(
            dataRepository = get(),
        )
    }
    viewModel {
        MapViewModel(
            dataRepository = get(),
        )
    }
    viewModel {
        NormalLoginViewModel(
            dataRepo = get(),
        )
    }
    viewModel {
        FriendSearchViewModel(
            dataRepository = get(),
        )
    }
    viewModel {
        ProfileViewModel(
            dataRepository = get(),
        )
    }
    viewModel{
        RegisterViewModel(
            dataRepository = get(),
        )
    }
}
