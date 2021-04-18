package com.zairussalamdev.moviebox.data.responses

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("name")
    val name: String = ""
)