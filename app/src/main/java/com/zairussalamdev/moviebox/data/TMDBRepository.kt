package com.zairussalamdev.moviebox.data

import com.zairussalamdev.moviebox.data.entities.DetailEntity
import com.zairussalamdev.moviebox.data.entities.MovieEntity
import com.zairussalamdev.moviebox.services.retrofit.TMDBService
import com.zairussalamdev.moviebox.utils.IdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TMDBRepository(private val apiService: TMDBService) {
    suspend fun getMovieList(): List<MovieEntity> {
        IdlingResource.increment()
        val response = withContext(Dispatchers.IO) { apiService.getNowPlayingMovies() }
        IdlingResource.decrement()
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

    suspend fun getTvShowsList(): List<MovieEntity> {
        IdlingResource.increment()
        val response = withContext(Dispatchers.IO) { apiService.getPopularTvShows() }
        IdlingResource.decrement()
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

    suspend fun getMovieDetail(id: Int): DetailEntity {
        IdlingResource.increment()
        val response = withContext(Dispatchers.IO) { apiService.getMovieDetail(id) }
        IdlingResource.decrement()
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

    suspend fun getTvShowDetail(id: Int): DetailEntity {
        IdlingResource.increment()
        val response = withContext(Dispatchers.IO) { apiService.getTvShowDetail(id) }
        IdlingResource.decrement()
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