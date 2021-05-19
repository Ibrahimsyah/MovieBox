package com.zairussalamdev.moviebox.core.data.source.remote.responses

import com.google.gson.annotations.SerializedName

data class Genre(
        @SerializedName("name")
        val name: String = ""
)