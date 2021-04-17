package com.zairussalamdev.moviebox.data

import com.zairussalamdev.moviebox.data.entities.DetailEntity
import com.zairussalamdev.moviebox.data.entities.MovieEntity
import com.zairussalamdev.moviebox.services.retrofit.TMDBService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TMDBRepository(private val apiService: TMDBService) {
    suspend fun getMovieList(): List<MovieEntity> {
        val response = withContext(Dispatchers.IO) { apiService.getNowPlayingMovies() }
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

    suspend fun getMovieDetail(id: Int): DetailEntity {
        val response = withContext(Dispatchers.IO) { apiService.getMovieDetail(id) }
        return DetailEntity(
                response.overview,
                response.title,
                response.posterPath,
                response.voteAverage,
                response.popularity,
                response.tagLine,
                response.id,
                response.homepage,
                response.status
        )
    }
}