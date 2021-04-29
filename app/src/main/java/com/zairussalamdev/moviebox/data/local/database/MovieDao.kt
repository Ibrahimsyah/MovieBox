package com.zairussalamdev.moviebox.data.local.database

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.zairussalamdev.moviebox.configs.Constants
import com.zairussalamdev.moviebox.data.local.entities.DetailEntity
import com.zairussalamdev.moviebox.data.local.entities.MovieEntity

@Dao
interface MovieDao {
    @Query("select * from movies where movieType = ${Constants.TYPE_MOVIE}")
    fun getMovies(): DataSource.Factory<Int, MovieEntity>

    @Query("select * from movies where movieType = ${Constants.TYPE_TV_SHOW}")
    fun getTvShows(): DataSource.Factory<Int, MovieEntity>

    @Query("select * from details where movie_id = :movieId and movieType = ${Constants.TYPE_MOVIE}")
    fun getMovieDetail(movieId: Int): LiveData<DetailEntity>

    @Query("select * from details where movie_id = :tvShowId and movieType = ${Constants.TYPE_TV_SHOW}")
    fun getTvShowDetail(tvShowId: Int): LiveData<DetailEntity>

    @Query("select * from movies where movieType= ${Constants.TYPE_MOVIE} and isFavorite = 1")
    fun getFavoriteMovies(): DataSource.Factory<Int, MovieEntity>

    @Query("select * from movies where movieType= ${Constants.TYPE_TV_SHOW} and isFavorite = 1")
    fun getFavoriteTvShows(): DataSource.Factory<Int, MovieEntity>

    @Insert
    fun insertMovie(movie: MovieEntity)

    @Insert
    fun insertDetail(movieDetail: DetailEntity)

    @Query("update movies set isFavorite = 1 where id = :id")
    fun addFavorite(id: Int)

    @Query("update movies set isFavorite = 0 where id = :id")
    fun removeFavorite(id: Int)

    @Query("select isFavorite == 1 from movies where id = :id")
    fun checkMovieFavorite(id: Int): LiveData<Boolean>
}