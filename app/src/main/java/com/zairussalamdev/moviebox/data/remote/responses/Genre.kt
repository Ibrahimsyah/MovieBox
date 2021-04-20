package com.zairussalamdev.moviebox.data.remote.responses

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("name")
    val name: String = ""
)