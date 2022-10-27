package com.pes.meetcatui

import android.app.Application
import com.pes.meetcatui.di.appModule
import com.pes.meetcatui.di.dataModule
import com.pes.meetcatui.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setUpDi()
    }

    private fun setUpDi() {
        startKoin {
            androidContext(this@BaseApplication)
            modules(appModule, networkModule, dataModule)
        }
    }
}
