package com.zairussalamdev.moviebox.core.utils

import com.zairussalamdev.moviebox.core.configs.Constants

object ImageNetwork {
    fun getThumbnailUrl(path: String): String = "${Constants.THUMBNAIL_IMAGE_URL}$path"

    fun getFullSizeUrl(path: String): String = "${Constants.FULL_SIZE_IMAGE_URL}$path"
}