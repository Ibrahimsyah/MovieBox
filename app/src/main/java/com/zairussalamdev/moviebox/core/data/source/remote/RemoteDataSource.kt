package com.zairussalamdev.moviebox.core.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zairussalamdev.moviebox.core.data.source.remote.network.ApiResponse
import com.zairussalamdev.moviebox.core.data.source.remote.network.TMDBService
import com.zairussalamdev.moviebox.core.data.source.remote.responses.DetailResponse
import com.zairussalamdev.moviebox.core.data.source.remote.responses.ListMovieResponse
import com.zairussalamdev.moviebox.core.data.source.remote.responses.ListTvShowResponse
import com.zairussalamdev.moviebox.core.utils.ErrorMessageHandler
import com.zairussalamdev.moviebox.core.utils.IdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteDataSource constructor(private val tmdbService: TMDBService) {
    suspend fun getMovieList(): LiveData<ApiResponse<ListMovieResponse>> {
        IdlingResource.increment()
        val result = MutableLiveData<ApiResponse<ListMovieResponse>>()
        try {
            val data = withContext(Dispatchers.IO) { tmdbService.getNowPlayingMovies() }
            if (data.movieResponses.isEmpty()) {
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

    suspend fun getTvShowList(): LiveData<ApiResponse<ListTvShowResponse>> {
        IdlingResource.increment()
        val result = MutableLiveData<ApiResponse<ListTvShowResponse>>()
        try {
            val data = withContext(Dispatchers.IO) { tmdbService.getPopularTvShows() }
            if (data.tvShowResponses.isEmpty()) {
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