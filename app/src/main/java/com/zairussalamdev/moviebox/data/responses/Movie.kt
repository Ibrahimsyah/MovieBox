package com.zairussalamdev.moviebox.data.responses

import com.google.gson.annotations.SerializedName

data class Movie(
        @SerializedName("overview")
        val overview: String,

        @SerializedName("title")
        val title: String,

        @SerializedName("poster_path")
        val posterPath: String,

        @SerializedName("vote_average")
        val voteAverage: Double,

        @SerializedName("id")
        val id: Int,
)