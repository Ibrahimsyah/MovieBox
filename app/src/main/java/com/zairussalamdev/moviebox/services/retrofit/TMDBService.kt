package com.zairussalamdev.moviebox.services.retrofit

import com.zairussalamdev.moviebox.BuildConfig
import com.zairussalamdev.moviebox.data.responses.MovieResponse
import com.zairussalamdev.moviebox.data.responses.TvShowResponse
import retrofit2.http.GET

const val apiKey: String = BuildConfig.API_KEY

interface TMDBService {
    @GET("movie/now_playing?api_key=$apiKey")
    suspend fun getNowPlayingMovies(): MovieResponse

    @GET("tv/popular?api_key=$apiKey")
    suspend fun getPopularTvShows(): TvShowResponse
}