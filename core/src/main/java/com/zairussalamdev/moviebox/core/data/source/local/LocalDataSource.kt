package com.zairussalamdev.moviebox.core.data.source.local

import com.zairussalamdev.moviebox.core.data.source.local.database.MovieDao
import com.zairussalamdev.moviebox.core.data.source.local.entities.DetailEntity
import com.zairussalamdev.moviebox.core.data.source.local.entities.MovieEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class LocalDataSource constructor(private val movieDao: MovieDao) {

    fun getMovies(): Flow<List<MovieEntity>> = movieDao.getMovies()

    fun getMovieDetail(movieId: Int): Flow<DetailEntity?> = movieDao.getMovieDetail(movieId)

    fun getTvShows(): Flow<List<MovieEntity>> = movieDao.getTvShows()

    fun getTvShowDetail(tvShowId: Int): Flow<DetailEntity?> = movieDao.getTvShowDetail(tvShowId)

    fun getFavoriteMovies(): Flow<List<MovieEntity>> = movieDao.getFavoriteMovies()

    fun getFavoriteTvShows(): Flow<List<MovieEntity>> = movieDao.getFavoriteTvShows()

    fun checkMovieFavorite(id: Int): Flow<Boolean> = movieDao.checkMovieFavorite(id)

    suspend fun insertMovie(movies: List<MovieEntity>) {
        withContext(Dispatchers.IO) { movieDao.insertMovie(movies) }
    }

    suspend fun insertDetailMovie(movieDetail: DetailEntity) {
        withContext(Dispatchers.IO) { movieDao.insertDetail(movieDetail) }
    }

    suspend fun addFavoriteMovie(id: Int) {
        withContext(Dispatchers.IO) { movieDao.addFavorite(id) }
    }

    suspend fun deleteFavoriteMovie(id: Int) {
        withContext(Dispatchers.IO) { movieDao.removeFavorite(id) }
    }
}