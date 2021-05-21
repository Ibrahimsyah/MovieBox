package com.zairussalamdev.moviebox.core.data.source.remote.responses

import com.google.gson.annotations.SerializedName

data class DetailResponse(

    @SerializedName("overview")
    val overview: String,

    @SerializedName("title", alternate = ["name"])
    val title: String,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("tagline")
    val tagLine: String,

    @SerializedName("genres")
    val genreResponses: List<GenreResponse>,

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("homepage")
    val homepage: String,

    @SerializedName("status")
    val status: String,

    @SerializedName("popularity")
    val popularity: Double
)