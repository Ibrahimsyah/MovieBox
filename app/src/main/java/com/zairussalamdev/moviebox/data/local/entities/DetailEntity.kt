package com.zairussalamdev.moviebox.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zairussalamdev.moviebox.configs.Constants

@Entity(tableName = "details")
data class DetailEntity(
        @PrimaryKey
        @ColumnInfo(name = "movie_id")
        val id: Int? = null,

        val overview: String? = null,
        val title: String? = null,
        val posterPath: String? = null,
        val voteAverage: Double? = null,
        val popularity: Double? = null,
        val tagLine: String? = null,
        val genres: List<String> = listOf(),
        val homepage: String? = null,
        val status: String? = null,
        val movieType: Int? = Constants.TYPE_MOVIE
)