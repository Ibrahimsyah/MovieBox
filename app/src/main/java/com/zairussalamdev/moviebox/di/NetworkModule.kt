package com.zairussalamdev.moviebox.di

import com.zairussalamdev.moviebox.configs.Constants
import com.zairussalamdev.moviebox.services.TMDBService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideTMDBService(retrofit: Retrofit): TMDBService = retrofit.create(TMDBService::class.java)
}