package com.zairussalamdev.moviebox

import android.app.Application
import com.zairussalamdev.moviebox.core.di.appModule
import com.zairussalamdev.moviebox.core.di.networkModule
import com.zairussalamdev.moviebox.core.di.storageModule
import com.zairussalamdev.moviebox.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(
                appModule,
                storageModule,
                networkModule,
                viewModelModule
            )
        }
    }
}