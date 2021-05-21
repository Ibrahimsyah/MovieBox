package com.zairussalamdev.moviebox.core.data.source.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zairussalamdev.moviebox.core.configs.Constants
import com.zairussalamdev.moviebox.core.data.source.local.entities.DetailEntity
import com.zairussalamdev.moviebox.core.data.source.local.entities.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("select * from movies where movieType = ${Constants.TYPE_MOVIE}")
    fun getMovies(): Flow<List<MovieEntity>>

    @Query("select * from movies where movieType = ${Constants.TYPE_TV_SHOW}")
    fun getTvShows(): Flow<List<MovieEntity>>

    @Query("select * from details where movie_id = :movieId and movieType = ${Constants.TYPE_MOVIE}")
    fun getMovieDetail(movieId: Int): Flow<DetailEntity?>

    @Query("select * from details where movie_id = :tvShowId and movieType = ${Constants.TYPE_TV_SHOW}")
    fun getTvShowDetail(tvShowId: Int): Flow<DetailEntity?>

    @Query("select * from movies where movieType = ${Constants.TYPE_MOVIE} and isFavorite = 1")
    fun getFavoriteMovies(): Flow<List<MovieEntity>>

    @Query("select * from movies where movieType = ${Constants.TYPE_TV_SHOW} and isFavorite = 1")
    fun getFavoriteTvShows(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovie(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDetail(movieDetail: DetailEntity)

    @Query("update movies set isFavorite = 1 where id = :id")
    fun addFavorite(id: Int)

    @Query("update movies set isFavorite = 0 where id = :id")
    fun removeFavorite(id: Int)

    @Query("select isFavorite == 1 from movies where id = :id")
    fun checkMovieFavorite(id: Int): Flow<Boolean>
}