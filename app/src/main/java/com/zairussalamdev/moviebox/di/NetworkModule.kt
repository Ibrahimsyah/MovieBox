package com.zairussalamdev.moviebox.di

import com.zairussalamdev.moviebox.services.retrofit.RetrofitService
import com.zairussalamdev.moviebox.services.retrofit.TMDBService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = RetrofitService.getInstance()

    @Singleton
    @Provides
    fun provideTMDBService(retrofit: Retrofit): TMDBService = retrofit.create(TMDBService::class.java)
}