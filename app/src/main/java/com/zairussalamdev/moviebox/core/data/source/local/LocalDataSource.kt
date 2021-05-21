package com.zairussalamdev.moviebox.core.data.source.local

import com.zairussalamdev.moviebox.core.data.source.local.database.MovieDao
import com.zairussalamdev.moviebox.core.data.source.local.entities.DetailEntity
import com.zairussalamdev.moviebox.core.data.source.local.entities.MovieEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class LocalDataSource constructor(private val movieDao: MovieDao) {
    suspend fun insertMovie(movies: List<MovieEntity>) {
        withContext(Dispatchers.IO) { movieDao.insertMovie(movies) }
    }

    suspend fun insertDetailMovie(movieDetail: DetailEntity) {
        withContext(Dispatchers.IO) { movieDao.insertDetail(movieDetail) }
    }

    fun getMovies(): Flow<List<MovieEntity>> {
        return movieDao.getMovies()
    }

    fun getMovieDetail(movieId: Int): Flow<DetailEntity?> {
        return movieDao.getMovieDetail(movieId)
    }

    fun getTvShows(): Flow<List<MovieEntity>> {
        return movieDao.getTvShows()
    }

    fun getTvShowDetail(tvShowId: Int): Flow<DetailEntity?> {
        return movieDao.getTvShowDetail(tvShowId)
    }

    fun getFavoriteMovies(): Flow<List<MovieEntity>> {
        return movieDao.getFavoriteMovies()
    }

    fun getFavoriteTvShows(): Flow<List<MovieEntity>> {
        return movieDao.getFavoriteTvShows()
    }

    fun checkMovieFavorite(id: Int): Flow<Boolean> {
        return movieDao.checkMovieFavorite(id)
    }

    suspend fun addFavoriteMovie(id: Int) {
        withContext(Dispatchers.IO) { movieDao.addFavorite(id) }
    }

    suspend fun deleteFavoriteMovie(id: Int) {
        withContext(Dispatchers.IO) { movieDao.removeFavorite(id) }
    }
}