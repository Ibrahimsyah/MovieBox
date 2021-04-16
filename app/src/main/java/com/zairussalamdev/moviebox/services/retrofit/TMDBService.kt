package com.zairussalamdev.moviebox.services.retrofit

import com.zairussalamdev.moviebox.BuildConfig
import com.zairussalamdev.moviebox.data.responses.MovieDetailResponse
import com.zairussalamdev.moviebox.data.responses.MovieResponse
import com.zairussalamdev.moviebox.data.responses.TvShowDetailResponse
import com.zairussalamdev.moviebox.data.responses.TvShowResponse
import retrofit2.http.GET
import retrofit2.http.Path

const val apiKey: String = BuildConfig.API_KEY

interface TMDBService {
    @GET("movie/now_playing?api_key=$apiKey")
    suspend fun getNowPlayingMovies(): MovieResponse

    @GET("tv/popular?api_key=$apiKey")
    suspend fun getPopularTvShows(): TvShowResponse

    @GET("movie/{movieId}?api_key=$apiKey")
    suspend fun getMovieDetail(@Path("movieId") movieId: Int): MovieDetailResponse

    @GET("tv/{tvId}?api_key=$apiKey")
    suspend fun getTvShowDetail(@Path("tvId") tvId: Int): TvShowDetailResponse
}