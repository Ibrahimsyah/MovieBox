package com.zairussalamdev.moviebox.data.remote

class ApiResponse<T>(val status: StatusResponse, val body: T, val message: String?) {
    companion object {
        fun <T> success(body: T) = ApiResponse(StatusResponse.SUCCESS, body, null)
        fun <T> empty(message: String, body: T) =
            ApiResponse(StatusResponse.EMPTY, body, message)

        fun <T> error(message: String, body: T) = ApiResponse(StatusResponse.ERROR, body, message)
    }
}
