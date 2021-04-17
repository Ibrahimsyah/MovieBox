package com.zairussalamdev.moviebox.utils

object ImageNetwork {
    fun getThumbnailUrl(path: String): String = "https://image.tmdb.org/t/p/w300/$path"

    fun getFullSizeUrl(path: String): String = "https://image.tmdb.org/t/p/w500/$path"
}