package com.pes.meetcatui.di

import android.content.Context

import com.pes.meetcatui.domain.DataRepository
import com.pes.meetcatui.domain.DataRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule = module {

    single<DataRepository> {
        DataRepositoryImpl(
            appScope = get(),
            meetcatApi = get(),
        )
    }
}
