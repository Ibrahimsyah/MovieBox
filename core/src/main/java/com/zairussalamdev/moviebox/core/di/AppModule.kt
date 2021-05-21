package com.zairussalamdev.moviebox.core.di

import com.zairussalamdev.moviebox.core.data.TMDBRepository
import com.zairussalamdev.moviebox.core.data.source.local.LocalDataSource
import com.zairussalamdev.moviebox.core.data.source.remote.RemoteDataSource
import com.zairussalamdev.moviebox.core.domain.repository.ITMDBRepository
import com.zairussalamdev.moviebox.core.domain.usecase.MovieInteractor
import com.zairussalamdev.moviebox.core.domain.usecase.MovieUseCase
import org.koin.dsl.module

val appModule = module {
    single<ITMDBRepository> { TMDBRepository(get(), get()) }

    single { RemoteDataSource(get()) }

    single { LocalDataSource(get()) }

    single<MovieUseCase> { MovieInteractor(get()) }
}