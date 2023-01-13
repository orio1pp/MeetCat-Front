package com.pes.meetcatui.di

import com.pes.meetcatui.feature_chat.presentation.screen_chat_list.ChatListViewModel
import com.pes.meetcatui.feature_chat.presentation.screen_friend_list.FriendsListViewModel
import com.pes.meetcatui.feature_chat.presentation.screen_user_search.UserSearchViewModel
import com.pes.meetcatui.feature_event.presentation.CreateOrEditEventViewModel
import com.pes.meetcatui.feature_event.presentation.EventListViewModel
import com.pes.meetcatui.feature_event.presentation.MapViewModel
import com.pes.meetcatui.feature_user.presentation.register_screen.RegisterViewModel
import com.pes.meetcatui.feature_event.presentation.admin_only.ReportedListViewModel
import com.pes.meetcatui.feature_event.presentation.user_events.EventListViewModelAttending
import com.pes.meetcatui.feature_event.presentation.user_events.EventListViewModelOwn
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
        CreateOrEditEventViewModel(
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
        UserSearchViewModel(
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
    viewModel {
        ChatListViewModel(
            dataRepository = get(),
        )
    }
    viewModel {
        FriendsListViewModel(
            dataRepository = get(),
        )
    }
    viewModel {
        EventListViewModelOwn(
            dataRepository = get(),
        )
    }
    viewModel {
        EventListViewModelAttending(
            dataRepository = get(),
        )
    }
    viewModel {
        FriendSearchViewModel(
            dataRepository = get(),
        )
    }
}
