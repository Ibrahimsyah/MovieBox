package com.zairussalamdev.moviebox.data

import com.zairussalamdev.moviebox.data.entities.MovieEntity
import com.zairussalamdev.moviebox.services.retrofit.TMDBService

class TMDBRepository(private val apiService: TMDBService) {
    suspend fun getMovieList(): List<MovieEntity> {
        val response = apiService.getNowPlayingMovies()
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
}