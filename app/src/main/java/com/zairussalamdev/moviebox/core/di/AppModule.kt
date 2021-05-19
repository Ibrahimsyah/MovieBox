package com.zairussalamdev.moviebox.core.di

import com.zairussalamdev.moviebox.core.data.TMDBRepository
import com.zairussalamdev.moviebox.core.data.source.local.LocalDataSource
import com.zairussalamdev.moviebox.core.data.source.remote.RemoteDataSource
import org.koin.dsl.module

val appModule = module {
    single { TMDBRepository(get(), get()) }

    single { RemoteDataSource(get()) }

    single { LocalDataSource(get()) }
}