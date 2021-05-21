package com.zairussalamdev.moviebox.core.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zairussalamdev.moviebox.core.data.source.local.entities.DetailEntity
import com.zairussalamdev.moviebox.core.data.source.local.entities.GenreConverter
import com.zairussalamdev.moviebox.core.data.source.local.entities.MovieEntity

@Database(entities = [MovieEntity::class, DetailEntity::class], version = 1)
@TypeConverters(GenreConverter::class)
abstract class MovieBoxDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}