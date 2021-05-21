package com.zairussalamdev.moviebox.core.data

sealed class Resource<T>(val data: T?, val message: String? = null) {
    class Success<T>(data: T) : com.zairussalamdev.moviebox.core.data.Resource<T>(data)

    class Error<T>(msg: String?, data: T? = null) : com.zairussalamdev.moviebox.core.data.Resource<T>(data, msg)

    class Loading<T>(data: T? = null) : com.zairussalamdev.moviebox.core.data.Resource<T>(data)
}