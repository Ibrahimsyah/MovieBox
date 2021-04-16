package com.zairussalamdev.moviebox.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zairussalamdev.moviebox.configs.MovieType

@Entity(tableName = "movies")
data class MovieEntity(
        @PrimaryKey
        @ColumnInfo(name = "id")
        val id: Int?,

        @ColumnInfo(name = "overview")
        val overview: String?,

        @ColumnInfo(name = "title")
        val title: String?,

        @ColumnInfo(name = "poster_path")
        val posterPath: String?,

        @ColumnInfo(name = "vote_average")
        val voteAverage: Double?,

        @ColumnInfo(name = "movie_type")
        val movieType: Int? = MovieType.TYPE_MOVIE
)
