package com.zairussalamdev.moviebox.core.data.source.remote.responses

import com.google.gson.annotations.SerializedName

data class TvShowResponse(
        @SerializedName("results")
        val tvShows: List<TvShow>
)