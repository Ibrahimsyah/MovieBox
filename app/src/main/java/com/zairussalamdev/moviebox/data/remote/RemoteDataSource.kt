package com.zairussalamdev.moviebox.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zairussalamdev.moviebox.data.remote.responses.DetailResponse
import com.zairussalamdev.moviebox.data.remote.responses.MovieResponse
import com.zairussalamdev.moviebox.data.remote.responses.TvShowResponse
import com.zairussalamdev.moviebox.services.TMDBService
import com.zairussalamdev.moviebox.utils.ErrorMessageHandler
import com.zairussalamdev.moviebox.utils.IdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val tmdbService: TMDBService) {
    suspend fun getMovieList(): LiveData<ApiResponse<MovieResponse>> {
        IdlingResource.increment()
        val result = MutableLiveData<ApiResponse<MovieResponse>>()
        try {
            val data = withContext(Dispatchers.IO) { tmdbService.getNowPlayingMovies() }
            if (data.movies.isEmpty()) {
                result.postValue(ApiResponse.empty("No Data Found", data))
            } else {
                result.postValue(ApiResponse.success(data))
            }
        } catch (error: Exception) {
            result.postValue(ApiResponse.error(ErrorMessageHandler.generateErrorMessage(error)))
        }
        IdlingResource.decrement()
        return result
    }

    suspend fun getTvShowList(): LiveData<ApiResponse<TvShowResponse>> {
        IdlingResource.increment()
        val result = MutableLiveData<ApiResponse<TvShowResponse>>()
        try {
            val data = withContext(Dispatchers.IO) { tmdbService.getPopularTvShows() }
            if (data.tvShows.isEmpty()) {
                result.postValue(ApiResponse.empty("No Data Found", data))
            } else {
                result.postValue(ApiResponse.success(data))
            }
        } catch (error: Exception) {
            result.postValue(ApiResponse.error(ErrorMessageHandler.generateErrorMessage(error)))
        }
        IdlingResource.decrement()
        return result
    }

    suspend fun getMovieDetail(id: Int): LiveData<ApiResponse<DetailResponse>> {
        IdlingResource.increment()
        val result = MutableLiveData<ApiResponse<DetailResponse>>()
        try {
            val data = withContext(Dispatchers.IO) { tmdbService.getMovieDetail(id) }
            result.postValue(ApiResponse.success(data))
        } catch (error: Exception) {
            result.postValue(ApiResponse.error(ErrorMessageHandler.generateErrorMessage(error)))
        }
        IdlingResource.decrement()
        return result
    }

    suspend fun getTvShowDetail(id: Int): LiveData<ApiResponse<DetailResponse>> {
        IdlingResource.increment()
        val result = MutableLiveData<ApiResponse<DetailResponse>>()
        try {
            val data = withContext(Dispatchers.IO) { tmdbService.getTvShowDetail(id) }
            result.postValue(ApiResponse.success(data))
        } catch (error: Exception) {
            result.postValue(ApiResponse.error(ErrorMessageHandler.generateErrorMessage(error)))
        }
        IdlingResource.decrement()
        return result
    }
}