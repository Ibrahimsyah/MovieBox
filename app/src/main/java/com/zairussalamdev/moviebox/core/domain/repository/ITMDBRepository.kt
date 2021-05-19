package com.zairussalamdev.moviebox.core.domain.repository

import androidx.lifecycle.LiveData
import com.zairussalamdev.moviebox.core.data.Resource
import com.zairussalamdev.moviebox.core.domain.model.Detail
import com.zairussalamdev.moviebox.core.domain.model.Movie

interface ITMDBRepository {
    fun getMovieList(): LiveData<Resource<List<Movie>>>

    fun getTvShowsList(): LiveData<Resource<List<Movie>>>

    fun getMovieDetail(id: Int): LiveData<Resource<Detail>>

    fun getTvShowDetail(id: Int): LiveData<Resource<Detail>>

    fun getFavoriteMovies(): LiveData<List<Movie>>

    fun getFavoriteTvShows(): LiveData<List<Movie>>

    fun checkMovieFavorite(id: Int): LiveData<Boolean>

    suspend fun insertFavoriteMovie(id: Int)

    suspend fun deleteFavoriteMovie(id: Int)
}