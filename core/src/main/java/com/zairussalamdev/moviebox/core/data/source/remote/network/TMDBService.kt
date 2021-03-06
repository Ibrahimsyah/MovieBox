package com.zairussalamdev.moviebox.core.data.source.remote.network

import com.zairussalamdev.moviebox.core.BuildConfig
import com.zairussalamdev.moviebox.core.data.source.remote.responses.DetailResponse
import com.zairussalamdev.moviebox.core.data.source.remote.responses.ListMovieResponse
import com.zairussalamdev.moviebox.core.data.source.remote.responses.ListTvShowResponse
import retrofit2.http.GET
import retrofit2.http.Path

const val apiKey: String = BuildConfig.API_KEY

interface TMDBService {
    @GET("movie/now_playing?api_key=$apiKey")
    suspend fun getNowPlayingMovies(): ListMovieResponse

    @GET("tv/popular?api_key=$apiKey")
    suspend fun getPopularTvShows(): ListTvShowResponse

    @GET("movie/{movieId}?api_key=$apiKey")
    suspend fun getMovieDetail(@Path("movieId") movieId: Int): DetailResponse

    @GET("tv/{tvId}?api_key=$apiKey")
    suspend fun getTvShowDetail(@Path("tvId") tvId: Int): DetailResponse
}