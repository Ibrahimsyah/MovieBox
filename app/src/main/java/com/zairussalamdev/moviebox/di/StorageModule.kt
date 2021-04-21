package com.zairussalamdev.moviebox.di

import android.content.Context
import androidx.room.Room
import com.zairussalamdev.moviebox.configs.Constants
import com.zairussalamdev.moviebox.data.local.database.MovieBoxDatabase
import com.zairussalamdev.moviebox.data.local.database.MovieDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule(private val context: Context) {
    @Singleton
    @Provides
    fun provideDatabase(): MovieBoxDatabase {
        return Room.databaseBuilder(context, MovieBoxDatabase::class.java, Constants.DB_NAME)
                .build()
    }

    @Singleton
    @Provides
    fun provideDao(database: MovieBoxDatabase): MovieDao = database.movieDao()
}