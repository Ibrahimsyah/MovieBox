package com.zairussalamdev.moviebox.data.local.database

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.zairussalamdev.moviebox.configs.Constants
import com.zairussalamdev.moviebox.data.local.entities.MovieEntity

@Dao
interface MovieDao {
    @Query("select * from movies where movieType = ${Constants.TYPE_MOVIE}")
    fun getFavoriteMovies(): DataSource.Factory<Int, MovieEntity>

    @Query("select * from movies where movieType = ${Constants.TYPE_TV_SHOW}")
    fun getFavoriteTvShows(): DataSource.Factory<Int, MovieEntity>

    @Insert
    fun insert(movie: MovieEntity)

    @Delete
    fun delete(movie: MovieEntity)

    @Query("select count(*) != 0 from movies where id = :id")
    fun checkMovieFavorite(id: Int): LiveData<Boolean>
}