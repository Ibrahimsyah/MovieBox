package com.zairussalamdev.moviebox.core.di

import androidx.room.Room
import com.zairussalamdev.moviebox.core.configs.Constants
import com.zairussalamdev.moviebox.core.data.source.local.database.MovieBoxDatabase
import com.zairussalamdev.moviebox.core.data.source.local.entities.GenreConverter
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val storageModule = module {
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes(Constants.DB_PASSPHRASE.toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(androidContext(), MovieBoxDatabase::class.java, Constants.DB_NAME)
            .addTypeConverter(GenreConverter())
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    single {
        get<MovieBoxDatabase>().movieDao()
    }
}