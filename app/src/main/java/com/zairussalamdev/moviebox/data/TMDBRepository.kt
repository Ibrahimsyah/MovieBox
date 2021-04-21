package com.zairussalamdev.moviebox.data

import androidx.lifecycle.LiveData
import com.zairussalamdev.moviebox.data.local.LocalDataSource
import com.zairussalamdev.moviebox.data.local.entities.DetailEntity
import com.zairussalamdev.moviebox.data.local.entities.MovieEntity
import com.zairussalamdev.moviebox.data.remote.RemoteDataSource
import javax.inject.Inject

class TMDBRepository @Inject constructor(
        private val remoteDataSource: RemoteDataSource,
        private val localDataSource: LocalDataSource
) : MovieDataSource {
    override suspend fun getMovieList(): List<MovieEntity> {
        val response = remoteDataSource.getMovieList()
        val result = ArrayList<MovieEntity>()
        response.movies.forEach { movie ->
            result.add(
                    MovieEntity(
                            overview = movie.overview,
                            title = movie.title,
                            posterPath = movie.posterPath,
                            voteAverage = movie.voteAverage,
                            id = movie.id
                    )
            )
        }
        return result.toList()
    }

    override suspend fun getTvShowsList(): List<MovieEntity> {
        val response = remoteDataSource.getTvShowList()
        val result = ArrayList<MovieEntity>()
        response.tvShows.forEach { movie ->
            result.add(
                    MovieEntity(
                            overview = movie.overview,
                            title = movie.name,
                            posterPath = movie.posterPath,
                            voteAverage = movie.voteAverage,
                            id = movie.id
                    )
            )
        }
        return result.toList()
    }

    override suspend fun getMovieDetail(id: Int): DetailEntity {
        val response = remoteDataSource.getMovieDetail(id)
        val genres = response.genres.map { genre -> genre.name }
        return DetailEntity(
                response.overview,
                response.title,
                response.posterPath,
                response.voteAverage,
                response.popularity,
                response.tagLine,
                genres = genres,
                response.id,
                response.homepage,
                response.status
        )
    }

    override suspend fun getTvShowDetail(id: Int): DetailEntity {
        val response = remoteDataSource.getTvShowDetail(id)
        val genres = response.genres.map { genre -> genre.name }
        return DetailEntity(
                response.overview,
                response.title,
                response.posterPath,
                response.voteAverage,
                response.popularity,
                response.tagLine,
                genres = genres,
                response.id,
                response.homepage,
                response.status
        )
    }

    override fun getFavoriteMovies(): LiveData<List<MovieEntity>> {
        return localDataSource.getFavoriteMovies()
    }

    override fun getFavoriteTvShows(): LiveData<List<MovieEntity>> {
        return localDataSource.getFavoriteTvShows()
    }

    override fun checkMovieFavorite(id: Int): LiveData<Boolean> {
        return localDataSource.checkMovieFavorite(id)
    }

    override suspend fun insertFavoriteMovie(movie: MovieEntity) {
        return localDataSource.insertFavoriteMovie(movie)
    }

    override suspend fun deleteFavoriteMovie(movie: MovieEntity) {
        return localDataSource.deleteFavoriteMovie(movie)
    }

}