package com.zairussalamdev.moviebox.data.responses

import com.google.gson.annotations.SerializedName

data class DetailResponse(

        @SerializedName("overview")
        val overview: String? = null,

        @SerializedName("title")
        val title: String? = null,

        @SerializedName("poster_path")
        val posterPath: String? = null,

        @SerializedName("vote_average")
        val voteAverage: Double? = null,

        @SerializedName("tagline")
        val tagLine: String? = "-",

        @SerializedName("id")
        val id: Int? = null,

        @SerializedName("homepage")
        val homepage: String? = "-",

        @SerializedName("status")
        val status: String? = "-",

        @SerializedName("popularity")
        val popularity: Double? = null
)