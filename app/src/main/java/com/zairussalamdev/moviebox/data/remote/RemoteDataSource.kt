package com.zairussalamdev.moviebox.data.remote

import com.zairussalamdev.moviebox.data.remote.responses.DetailResponse
import com.zairussalamdev.moviebox.data.remote.responses.MovieResponse
import com.zairussalamdev.moviebox.data.remote.responses.TvShowResponse
import com.zairussalamdev.moviebox.services.TMDBService
import com.zairussalamdev.moviebox.utils.IdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val tmdbService: TMDBService) {
    suspend fun getMovieList(): MovieResponse {
        IdlingResource.increment()
        val result = withContext(Dispatchers.IO) { tmdbService.getNowPlayingMovies() }
        IdlingResource.decrement()
        return result
    }

    suspend fun getTvShowList(): TvShowResponse {
        IdlingResource.increment()
        val result = withContext(Dispatchers.IO) { tmdbService.getPopularTvShows() }
        IdlingResource.decrement()
        return result
    }

    suspend fun getMovieDetail(id: Int): DetailResponse {
        IdlingResource.increment()
        val result = withContext(Dispatchers.IO) { tmdbService.getMovieDetail(id) }
        IdlingResource.decrement()
        return result
    }

    suspend fun getTvShowDetail(id: Int): DetailResponse {
        IdlingResource.increment()
        val result = withContext(Dispatchers.IO) { tmdbService.getTvShowDetail(id) }
        IdlingResource.decrement()
        return result
    }
}