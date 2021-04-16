package com.zairussalamdev.moviebox.data.entities

import com.zairussalamdev.moviebox.configs.MovieType

data class MovieEntity(
        val id: Int?,
        val overview: String?,
        val title: String?,
        val posterPath: String?,
        val voteAverage: Double?,
        val movieType: Int? = MovieType.TYPE_MOVIE
)
