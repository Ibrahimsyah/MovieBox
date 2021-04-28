package com.zairussalamdev.moviebox.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zairussalamdev.moviebox.data.remote.responses.DetailResponse
import com.zairussalamdev.moviebox.data.remote.responses.MovieResponse
import com.zairussalamdev.moviebox.data.remote.responses.TvShowResponse
import com.zairussalamdev.moviebox.services.TMDBService
import com.zairussalamdev.moviebox.utils.IdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val tmdbService: TMDBService) {
    suspend fun getMovieList(): LiveData<ApiResponse<MovieResponse>> {
        IdlingResource.increment()
        val result = MutableLiveData<ApiResponse<MovieResponse>>()
        val data = withContext(Dispatchers.IO) { tmdbService.getNowPlayingMovies() }
        if (data.movies.isEmpty()) {
            result.postValue(ApiResponse.empty("No Data Found", data))
        } else {
            result.postValue(ApiResponse.success(data))
        }
        IdlingResource.decrement()
        return result
    }

    suspend fun getTvShowList(): LiveData<ApiResponse<TvShowResponse>> {
        IdlingResource.increment()
        val result = MutableLiveData<ApiResponse<TvShowResponse>>()
        val data = withContext(Dispatchers.IO) { tmdbService.getPopularTvShows() }
        if (data.tvShows.isEmpty()) {
            result.postValue(ApiResponse.empty("No Data Found", data))
        } else {
            result.postValue(ApiResponse.success(data))
        }
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