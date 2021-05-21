package com.zairussalamdev.moviebox.core.domain.repository

import com.zairussalamdev.moviebox.core.data.Resource
import com.zairussalamdev.moviebox.core.domain.model.Detail
import com.zairussalamdev.moviebox.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface ITMDBRepository {
    fun getMovieList(): Flow<Resource<List<Movie>>>

    fun getTvShowsList(): Flow<Resource<List<Movie>>>

    fun getMovieDetail(id: Int): Flow<Resource<Detail>>

    fun getTvShowDetail(id: Int): Flow<Resource<Detail>>

    fun getFavoriteMovies(): Flow<List<Movie>>

    fun getFavoriteTvShows(): Flow<List<Movie>>

    fun checkMovieFavorite(id: Int): Flow<Boolean>

    suspend fun insertFavoriteMovie(id: Int)

    suspend fun deleteFavoriteMovie(id: Int)
}