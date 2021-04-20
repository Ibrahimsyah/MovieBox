package com.zairussalamdev.moviebox.data

import com.zairussalamdev.moviebox.data.entities.DetailEntity
import com.zairussalamdev.moviebox.data.entities.MovieEntity
import com.zairussalamdev.moviebox.data.remote.RemoteDataSource
import javax.inject.Inject

class TMDBRepository @Inject constructor(private val remoteDataSource: RemoteDataSource) : MovieDataSource {
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
}