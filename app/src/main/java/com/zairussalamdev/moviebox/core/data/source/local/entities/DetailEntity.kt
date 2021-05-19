package com.zairussalamdev.moviebox.core.data.source.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zairussalamdev.moviebox.core.configs.Constants

@Entity(tableName = "details")
data class DetailEntity(
    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    val id: Int,

    val overview: String,
    val title: String,
    val posterPath: String,
    val voteAverage: Double,
    val popularity: Double,
    val tagLine: String,
    val genres: List<String>,
    val homepage: String,
    val status: String,
    val movieType: Int = Constants.TYPE_MOVIE
)