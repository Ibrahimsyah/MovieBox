package com.zairussalamdev.moviebox.data.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.zairussalamdev.moviebox.data.local.database.MovieDao
import com.zairussalamdev.moviebox.data.local.entities.MovieEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val movieDao: MovieDao) {
    fun getMovies(): DataSource.Factory<Int, MovieEntity> {
        return movieDao.getMovies()
    }

    fun getTvShows(): DataSource.Factory<Int, MovieEntity> {
        return movieDao.getTvShows()
    }

    fun checkMovieFavorite(id: Int): LiveData<Boolean> {
        return movieDao.checkMovieFavorite(id)
    }

    suspend fun insertMovie(movieEntity: MovieEntity) {
        withContext(Dispatchers.IO) { movieDao.insert(movieEntity) }
    }

    suspend fun deleteFavoriteMovie(movieEntity: MovieEntity) {
        withContext(Dispatchers.IO) { movieDao.delete(movieEntity) }
    }
}