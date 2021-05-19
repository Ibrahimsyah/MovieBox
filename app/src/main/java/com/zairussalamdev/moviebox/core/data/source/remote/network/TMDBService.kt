package com.zairussalamdev.moviebox.core.data.source.remote.network

import com.zairussalamdev.moviebox.BuildConfig
import com.zairussalamdev.moviebox.core.data.source.remote.responses.DetailResponse
import com.zairussalamdev.moviebox.core.data.source.remote.responses.MovieResponse
import com.zairussalamdev.moviebox.core.data.source.remote.responses.TvShowResponse
import retrofit2.http.GET
import retrofit2.http.Path

const val apiKey: String = BuildConfig.API_KEY

interface TMDBService {
    @GET("movie/now_playing?api_key=$apiKey")
    suspend fun getNowPlayingMovies(): MovieResponse

    @GET("tv/popular?api_key=$apiKey")
    suspend fun getPopularTvShows(): TvShowResponse

    @GET("movie/{movieId}?api_key=$apiKey")
    suspend fun getMovieDetail(@Path("movieId") movieId: Int): DetailResponse

    @GET("tv/{tvId}?api_key=$apiKey")
    suspend fun getTvShowDetail(@Path("tvId") tvId: Int): DetailResponse
}