package com.zairussalamdev.moviebox.core.domain.model

data class Detail(
    val overview: String,
    val title: String,
    val posterPath: String,
    val voteAverage: Double,
    val tagLine: String,
    val genres: List<Genre>,
    val id: Int,
    val homepage: String,
    val status: String,
    val popularity: Double
)