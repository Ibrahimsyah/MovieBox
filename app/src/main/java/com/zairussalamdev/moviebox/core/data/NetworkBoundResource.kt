package com.zairussalamdev.moviebox.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.zairussalamdev.moviebox.core.data.source.remote.network.ApiResponse

@Suppress("UNCHECKED_CAST")
abstract class NetworkBoundResource<ResultType, RequestType> {
    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.postValue(Resource.Loading(null))

        @Suppress("LeakingThis")
        val dataFromDb = populateDataFromDb()
        result.addSource(dataFromDb) {
            if (shouldFetch(it)) {
                fetchDataFromNetwork(dataFromDb)
            } else {
                result.postValue(Resource.Success(it))
            }
        }
    }

    fun asLiveData() = result

    protected abstract fun populateDataFromDb(): LiveData<ResultType>
    protected abstract fun shouldFetch(data: ResultType?): Boolean
    protected abstract fun networkCall(): LiveData<ApiResponse<RequestType>>
    protected abstract fun saveCallResult(data: RequestType)
    private fun fetchDataFromNetwork(dataFromDb: LiveData<ResultType>) {
        val apiResponse = networkCall()
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dataFromDb)
            when (response) {
                is ApiResponse.Success -> {
                    saveCallResult(response.data)
                    result.addSource(populateDataFromDb()) { dbData ->
                        if (dbData != null) {
                            result.postValue(Resource.Success(dbData))
                        }
                    }
                }

                is ApiResponse.Empty -> {
                    result.addSource(populateDataFromDb()) { dbData ->
                        result.postValue(Resource.Success(dbData))
                    }
                }
                is ApiResponse.Error -> {
                    result.addSource(dataFromDb) {
                        result.postValue(Resource.Error(response.errorMessage, null))
                    }
                }
            }
        }
    }
}