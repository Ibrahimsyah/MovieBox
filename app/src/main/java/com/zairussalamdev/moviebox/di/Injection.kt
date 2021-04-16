package com.zairussalamdev.moviebox.di

import com.zairussalamdev.moviebox.data.TMDBRepository
import com.zairussalamdev.moviebox.services.retrofit.RetrofitService
import com.zairussalamdev.moviebox.services.retrofit.TMDBService

object Injection {
    fun provideRepository(): TMDBRepository {
        val apiService = RetrofitService.getInstance().create(TMDBService::class.java)
        return TMDBRepository(apiService)
    }
}