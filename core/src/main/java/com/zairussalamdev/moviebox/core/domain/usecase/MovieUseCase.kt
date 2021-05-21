package com.zairussalamdev.moviebox.core.domain.usecase

import androidx.lifecycle.LiveData
import com.zairussalamdev.moviebox.core.data.Resource
import com.zairussalamdev.moviebox.core.domain.model.Detail
import com.zairussalamdev.moviebox.core.domain.model.Movie

interface MovieUseCase {
    fun getAllMovies(): LiveData<Resource<List<Movie>>>
    fun getAllTvShows(): LiveData<Resource<List<Movie>>>
    fun getMovieDetail(movieId: Int): LiveData<Resource<Detail>>
    fun getTvShowDetail(tvShowId: Int): LiveData<Resource<Detail>>
    fun getFavoriteMovies(): LiveData<List<Movie>>
    fun getFavoriteTvShows(): LiveData<List<Movie>>
    fun checkMovieFavorite(movieId: Int): LiveData<Boolean>
    suspend fun addMovieToFavorite(movieId: Int)
    suspend fun removeMovieFromFavorite(movieId: Int)
}
