package com.zairussalamdev.moviebox.core.data.source.remote.responses

import com.google.gson.annotations.SerializedName

data class ListMovieResponse(
    @SerializedName("results")
    val movieResponses: List<MovieResponse> = listOf()
)