package com.zairussalamdev.moviebox.core.data.source.local

import androidx.lifecycle.LiveData
import com.zairussalamdev.moviebox.core.data.source.local.database.MovieDao
import com.zairussalamdev.moviebox.core.data.source.local.entities.DetailEntity
import com.zairussalamdev.moviebox.core.data.source.local.entities.MovieEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalDataSource constructor(private val movieDao: MovieDao) {
    suspend fun insertMovie(movies: List<MovieEntity>) {
        withContext(Dispatchers.IO) { movieDao.insertMovie(movies) }
    }

    suspend fun insertDetailMovie(movieDetail: DetailEntity) {
        withContext(Dispatchers.IO) { movieDao.insertDetail(movieDetail) }
    }

    fun getMovies(): LiveData<List<MovieEntity>> {
        return movieDao.getMovies()
    }

    fun getMovieDetail(movieId: Int): LiveData<DetailEntity> {
        return movieDao.getMovieDetail(movieId)
    }

    fun getTvShows(): LiveData<List<MovieEntity>> {
        return movieDao.getTvShows()
    }

    fun getTvShowDetail(tvShowId: Int): LiveData<DetailEntity> {
        return movieDao.getTvShowDetail(tvShowId)
    }

    fun getFavoriteMovies(): LiveData<List<MovieEntity>> {
        return movieDao.getFavoriteMovies()
    }

    fun getFavoriteTvShows(): LiveData<List<MovieEntity>> {
        return movieDao.getFavoriteTvShows()
    }

    fun checkMovieFavorite(id: Int): LiveData<Boolean> {
        return movieDao.checkMovieFavorite(id)
    }

    suspend fun addFavoriteMovie(id: Int) {
        withContext(Dispatchers.IO) { movieDao.addFavorite(id) }
    }

    suspend fun deleteFavoriteMovie(id: Int) {
        withContext(Dispatchers.IO) { movieDao.removeFavorite(id) }
    }
}