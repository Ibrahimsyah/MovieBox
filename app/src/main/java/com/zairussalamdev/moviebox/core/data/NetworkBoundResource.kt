package com.zairussalamdev.moviebox.core.data

import com.zairussalamdev.moviebox.core.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.*

@Suppress("UNCHECKED_CAST")
abstract class NetworkBoundResource<ResultType, RequestType> {

    private val result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        val dataFromDb = populateDataFromDb().first()
        if (shouldFetch(dataFromDb)) {
            emit(Resource.Loading())
            when (val response = networkCall().first()) {
                is ApiResponse.Success -> {
                    saveCallResult(response.data)
                    emitAll(populateDataFromDb().map { Resource.Success(it) })
                }

                is ApiResponse.Empty -> {
                    emitAll(populateDataFromDb().map { Resource.Success(it) })
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error<ResultType>(response.errorMessage))
                }
            }
        } else {
            emitAll(populateDataFromDb().map { Resource.Success(it) })
        }
    }

    fun build() = result

    protected abstract fun populateDataFromDb(): Flow<ResultType>
    protected abstract fun shouldFetch(data: ResultType?): Boolean
    protected abstract suspend fun networkCall(): Flow<ApiResponse<RequestType>>
    protected abstract suspend fun saveCallResult(data: RequestType)
}