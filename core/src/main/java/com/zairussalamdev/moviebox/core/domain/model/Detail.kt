package com.zairussalamdev.moviebox.core.domain.model

import com.zairussalamdev.moviebox.core.configs.Constants

data class Detail(
    val overview: String = "",
    val title: String = "",
    val posterPath: String = "",
    val voteAverage: Double = 0.0,
    val tagLine: String = "",
    val genres: List<Genre> = listOf(),
    val id: Int,
    val homepage: String = "",
    val status: String = "",
    val popularity: Double = 0.0,
    val movieType: Int = Constants.TYPE_MOVIE
) {
    companion object {
        fun createEmptyObject(): Detail = Detail(id = -1)
    }
}