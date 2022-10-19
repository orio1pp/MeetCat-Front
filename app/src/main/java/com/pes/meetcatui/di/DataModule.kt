package com.pes.meetcatui.di

import com.pes.meetcatui.feature_event.domain.DataRepository
import com.pes.meetcatui.feature_event.domain.DataRepositoryImpl
import org.koin.dsl.module

val dataModule = module {

    single<DataRepository> {
        DataRepositoryImpl(
            appScope = get(),
            meetcatApi = get(),
        )
    }
}
