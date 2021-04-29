package com.zairussalamdev.moviebox.data.remote

class ApiResponse<T>(val status: StatusResponse, val body: T?, val message: String?) {
    companion object {
        fun <T> success(body: T) = ApiResponse(StatusResponse.SUCCESS, body, null)
        fun <T> empty(message: String, body: T) =
                ApiResponse(StatusResponse.EMPTY, body, message)

        @Suppress("UNCHECKED_CAST")
        fun <T> error(message: String) = ApiResponse(StatusResponse.ERROR, null as T, message)
    }
}
