package com.zairussalamdev.moviebox.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zairussalamdev.moviebox.configs.Constants

@Entity(tableName = "movies")
data class MovieEntity(
        @PrimaryKey val id: Int?,
        val overview: String?,
        val title: String?,
        val posterPath: String?,
        val voteAverage: Double?,
        val movieType: Int? = Constants.TYPE_MOVIE
)
