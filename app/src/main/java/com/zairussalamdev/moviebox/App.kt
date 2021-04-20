package com.zairussalamdev.moviebox

import android.app.Application
import com.zairussalamdev.moviebox.di.AppComponent
import com.zairussalamdev.moviebox.di.DaggerAppComponent

class App : Application() {
    val appComponent: AppComponent = DaggerAppComponent.builder()
            .build()
}