package com.zairussalamdev.moviebox.core.data.source.remote

import com.zairussalamdev.moviebox.core.data.source.remote.network.ApiResponse
import com.zairussalamdev.moviebox.core.data.source.remote.network.TMDBService
import com.zairussalamdev.moviebox.core.data.source.remote.responses.DetailResponse
import com.zairussalamdev.moviebox.core.data.source.remote.responses.ListMovieResponse
import com.zairussalamdev.moviebox.core.data.source.remote.responses.ListTvShowResponse
import com.zairussalamdev.moviebox.core.utils.ErrorMessageHandler
import com.zairussalamdev.moviebox.core.utils.IdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource constructor(private val tmdbService: TMDBService) {
    suspend fun getMovieList(): Flow<ApiResponse<ListMovieResponse>> {
        return flow {
            try {
                IdlingResource.increment()
                val data = tmdbService.getNowPlayingMovies()
                if (data.movieResponses.isEmpty()) {
                    emit(ApiResponse.Empty)
                } else {
                    emit(ApiResponse.Success(data))
                }
            } catch (error: Exception) {
                emit(ApiResponse.Error(ErrorMessageHandler.generateErrorMessage(error)))
            } finally {
                IdlingResource.decrement()
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getTvShowList(): Flow<ApiResponse<ListTvShowResponse>> {
        return flow {
            try {
                IdlingResource.increment()
                val data = tmdbService.getPopularTvShows()
                if (data.tvShowResponses.isEmpty()) {
                    emit(ApiResponse.Empty)
                } else {
                    emit(ApiResponse.Success(data))
                }
            } catch (error: Exception) {
                emit(ApiResponse.Error(ErrorMessageHandler.generateErrorMessage(error)))
            } finally {
                IdlingResource.decrement()
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMovieDetail(id: Int): Flow<ApiResponse<DetailResponse>> {
        return flow {
            try {
                IdlingResource.increment()
                val data = tmdbService.getMovieDetail(id)
                emit(ApiResponse.Success(data))
            } catch (error: Exception) {
                emit(ApiResponse.Error(ErrorMessageHandler.generateErrorMessage(error)))
            } finally {
                IdlingResource.decrement()
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getTvShowDetail(id: Int): Flow<ApiResponse<DetailResponse>> {
        return flow {
            try {
                IdlingResource.increment()
                val data = tmdbService.getTvShowDetail(id)
                emit(ApiResponse.Success(data))
            } catch (error: Exception) {
                emit(ApiResponse.Error(ErrorMessageHandler.generateErrorMessage(error)))
            } finally {
                IdlingResource.decrement()
            }
        }.flowOn(Dispatchers.IO)
    }
}