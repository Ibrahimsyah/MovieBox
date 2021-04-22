package com.zairussalamdev.moviebox.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.zairussalamdev.moviebox.data.local.entities.DetailEntity
import com.zairussalamdev.moviebox.data.local.entities.MovieEntity

interface MovieDataSource {
    suspend fun getMovieList(): List<MovieEntity>

    suspend fun getTvShowsList(): List<MovieEntity>

    suspend fun getMovieDetail(id: Int): DetailEntity

    suspend fun getTvShowDetail(id: Int): DetailEntity

    fun getFavoriteMovies(): LiveData<PagedList<MovieEntity>>

    fun getFavoriteTvShows(): LiveData<PagedList<MovieEntity>>

    fun checkMovieFavorite(id: Int): LiveData<Boolean>

    suspend fun insertFavoriteMovie(movie: MovieEntity)

    suspend fun deleteFavoriteMovie(movie: MovieEntity)
}
