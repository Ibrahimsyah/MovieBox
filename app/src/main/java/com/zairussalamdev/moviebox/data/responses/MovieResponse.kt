package com.zairussalamdev.moviebox.data.responses

import com.google.gson.annotations.SerializedName

data class MovieResponse(
        @SerializedName("results")
        val movies: List<Movie>
)