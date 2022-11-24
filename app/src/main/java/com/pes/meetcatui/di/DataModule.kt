package com.pes.meetcatui.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.pes.meetcatui.feature_event.data.DATA_PREFERENCES_NAME
import com.pes.meetcatui.feature_event.data.DataPreferences
import com.pes.meetcatui.feature_event.data.DataPreferencesImpl
import com.pes.meetcatui.feature_event.domain.*
import com.pes.meetcatui.feature_user.domain.DataRepositoryUsers
import com.pes.meetcatui.feature_user.domain.DataRepositoryUsersImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

private val Context.dataPreferences: DataStore<Preferences> by preferencesDataStore(DATA_PREFERENCES_NAME)

val dataModule = module {
    single<DataRepository> {
        DataRepositoryImpl(
            appScope = get(),
            meetcatApi = get(),
            dataPreferences = get(),
        )
    }

    single<DataPreferences> {
        DataPreferencesImpl(
            dataStore = androidApplication().dataPreferences,
        )
    }

    single<DataRepositoryUsers> {
        DataRepositoryUsersImpl(
            meetCatApi = get(),
        )
    }
}
