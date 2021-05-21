package com.zairussalamdev.moviebox.core.data.source.remote.responses

import com.google.gson.annotations.SerializedName

data class ListTvShowResponse(
    @SerializedName("results")
    val tvShowResponses: List<TvShowResponse>
)