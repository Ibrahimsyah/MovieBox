package com.zairussalamdev.moviebox.data.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.zairussalamdev.moviebox.data.local.database.MovieDao
import com.zairussalamdev.moviebox.data.local.entities.MovieEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val movieDao: MovieDao) {
    suspend fun insertMovie(movie: MovieEntity) {
        withContext(Dispatchers.IO) { movieDao.insert(movie) }
    }

    fun getMovies(): DataSource.Factory<Int, MovieEntity> {
        return movieDao.getMovies()
    }

    fun getTvShows(): DataSource.Factory<Int, MovieEntity> {
        return movieDao.getTvShows()
    }

    fun getFavoriteMovies(): DataSource.Factory<Int, MovieEntity> {
        return movieDao.getFavoriteMovies()
    }

    fun getFavoriteTvShows(): DataSource.Factory<Int, MovieEntity> {
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