package com.zairussalamdev.moviebox.core.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.zairussalamdev.moviebox.core.data.source.local.entities.DetailEntity
import com.zairussalamdev.moviebox.core.data.source.local.entities.MovieEntity

interface MovieDataSource {
    fun getMovieList(): LiveData<Resource<PagedList<MovieEntity>>>

    fun getTvShowsList(): LiveData<Resource<PagedList<MovieEntity>>>

    fun getMovieDetail(id: Int): LiveData<Resource<DetailEntity>>

    fun getTvShowDetail(id: Int): LiveData<Resource<DetailEntity>>

    fun getFavoriteMovies(): LiveData<PagedList<MovieEntity>>

    fun getFavoriteTvShows(): LiveData<PagedList<MovieEntity>>

    fun checkMovieFavorite(id: Int): LiveData<Boolean>

    suspend fun insertFavoriteMovie(id: Int)

    suspend fun deleteFavoriteMovie(id: Int)
}
