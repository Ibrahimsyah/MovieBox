package com.zairussalamdev.moviebox.data.local

import androidx.lifecycle.LiveData
import com.zairussalamdev.moviebox.data.local.database.MovieDao
import com.zairussalamdev.moviebox.data.local.entities.MovieEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val movieDao: MovieDao) {
    fun getFavoriteMovies(): LiveData<List<MovieEntity>> {
        return movieDao.getFavoriteMovies()
    }

    fun getFavoriteTvShows(): LiveData<List<MovieEntity>> {
        return movieDao.getFavoriteTvShows()
    }

    fun checkMovieFavorite(id: Int): LiveData<Boolean> {
        return movieDao.checkMovieFavorite(id)
    }

    suspend fun insertFavoriteMovie(movieEntity: MovieEntity) {
        withContext(Dispatchers.IO) { movieDao.insert(movieEntity) }
    }

    suspend fun deleteFavoriteMovie(movieEntity: MovieEntity) {
        withContext(Dispatchers.IO) { movieDao.delete(movieEntity) }
    }
}