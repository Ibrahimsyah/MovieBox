package com.zairussalamdev.moviebox.core.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zairussalamdev.moviebox.core.data.source.remote.network.ApiResponse
import com.zairussalamdev.moviebox.core.data.source.remote.network.TMDBService
import com.zairussalamdev.moviebox.core.data.source.remote.responses.DetailResponse
import com.zairussalamdev.moviebox.core.data.source.remote.responses.MovieResponse
import com.zairussalamdev.moviebox.core.data.source.remote.responses.TvShowResponse
import com.zairussalamdev.moviebox.core.utils.ErrorMessageHandler
import com.zairussalamdev.moviebox.core.utils.IdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteDataSource constructor(private val tmdbService: TMDBService) {
    suspend fun getMovieList(): LiveData<ApiResponse<MovieResponse>> {
        IdlingResource.increment()
        val result = MutableLiveData<ApiResponse<MovieResponse>>()
        try {
            val data = withContext(Dispatchers.IO) { tmdbService.getNowPlayingMovies() }
            if (data.movies.isEmpty()) {
                result.postValue(ApiResponse.Empty)
            } else {
                result.postValue(ApiResponse.Success(data))
            }
        } catch (error: Exception) {
            result.postValue(ApiResponse.Error(ErrorMessageHandler.generateErrorMessage(error)))
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
                result.postValue(ApiResponse.Empty)
            } else {
                result.postValue(ApiResponse.Success(data))
            }
        } catch (error: Exception) {
            result.postValue(ApiResponse.Error(ErrorMessageHandler.generateErrorMessage(error)))
        }
        IdlingResource.decrement()
        return result
    }

    suspend fun getMovieDetail(id: Int): LiveData<ApiResponse<DetailResponse>> {
        IdlingResource.increment()
        val result = MutableLiveData<ApiResponse<DetailResponse>>()
        try {
            val data = withContext(Dispatchers.IO) { tmdbService.getMovieDetail(id) }
            result.postValue(ApiResponse.Success(data))
        } catch (error: Exception) {
            result.postValue(ApiResponse.Error(ErrorMessageHandler.generateErrorMessage(error)))
        }
        IdlingResource.decrement()
        return result
    }

    suspend fun getTvShowDetail(id: Int): LiveData<ApiResponse<DetailResponse>> {
        IdlingResource.increment()
        val result = MutableLiveData<ApiResponse<DetailResponse>>()
        try {
            val data = withContext(Dispatchers.IO) { tmdbService.getTvShowDetail(id) }
            result.postValue(ApiResponse.Success(data))
        } catch (error: Exception) {
            result.postValue(ApiResponse.Error(ErrorMessageHandler.generateErrorMessage(error)))
        }
        IdlingResource.decrement()
        return result
    }
}