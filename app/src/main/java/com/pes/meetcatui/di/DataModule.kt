package com.pes.meetcatui.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.pes.meetcatui.feature_chat.domain.DataRepositoryChats
import com.pes.meetcatui.feature_chat.domain.DataRepositoryChatsImpl
import com.pes.meetcatui.feature_user.data.DATA_PREFERENCES_NAME
import com.pes.meetcatui.feature_user.data.DataPreferences
import com.pes.meetcatui.feature_user.data.DataPreferencesImpl
import com.pes.meetcatui.feature_event.domain.*
import com.pes.meetcatui.feature_user.domain.DataRepositoryUsers
import com.pes.meetcatui.feature_user.domain.DataRepositoryUsersImpl
import com.pes.meetcatui.network.MeetCatApiInterceptor
import okhttp3.Interceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

private val Context.dataPreferences: DataStore<Preferences> by preferencesDataStore(
    DATA_PREFERENCES_NAME
)

val dataModule = module {
    single<DataRepository> {
        DataRepositoryImpl(
            meetcatApi = get(),
        )
    }

    single<DataPreferences> {
        DataPreferencesImpl(
            dataStore = androidApplication().dataPreferences,
        )
    }

    single<DataRepositoryUsers> {
        DataRepositoryUsersImpl(
            appScope = get(),
            meetCatApi = get(),
            dataPreferences = get(),
        )
    }

    single<DataRepositoryChats> {
        DataRepositoryChatsImpl(
            appScope = get(),
            meetCatApi = get(),
            dataPreferences = get(),
        )
    }

    single<Interceptor> {
        MeetCatApiInterceptor(
            appScope = get(),
            dataPreferences = get()
        )
    }
}
