package com.zairussalamdev.moviebox.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zairussalamdev.moviebox.data.local.entities.DetailEntity
import com.zairussalamdev.moviebox.data.local.entities.MovieEntity
import com.zairussalamdev.moviebox.data.remote.responses.GenreConverter

@Database(entities = [MovieEntity::class, DetailEntity::class], version = 1)
@TypeConverters(GenreConverter::class)
abstract class MovieBoxDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}