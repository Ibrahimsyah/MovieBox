package com.zairussalamdev.moviebox.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zairussalamdev.moviebox.data.local.entities.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class MovieBoxDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}