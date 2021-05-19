package com.zairussalamdev.moviebox.core.data.source.remote.responses

import com.google.gson.annotations.SerializedName

data class MovieResponse(
        @SerializedName("results")
        val movies: List<Movie>
)