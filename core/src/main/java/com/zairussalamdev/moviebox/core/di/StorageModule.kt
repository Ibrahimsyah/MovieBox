package com.zairussalamdev.moviebox.core.di

import androidx.room.Room
import com.zairussalamdev.moviebox.core.configs.Constants
import com.zairussalamdev.moviebox.core.data.source.local.database.MovieBoxDatabase
import com.zairussalamdev.moviebox.core.data.source.local.entities.GenreConverter
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val storageModule = module {
    single {
        Room.databaseBuilder(androidContext(), MovieBoxDatabase::class.java, Constants.DB_NAME)
            .addTypeConverter(GenreConverter())
            .build()
    }

    single {
        get<MovieBoxDatabase>().movieDao()
    }
}