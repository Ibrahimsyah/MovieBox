package com.zairussalamdev.moviebox.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.zairussalamdev.moviebox.vo.Resource

@Suppress("UNCHECKED_CAST")
abstract class NetworkBoundResource<ResultType, RequestType> {
    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.postValue(Resource.loading(null))

        @Suppress("LeakingThis")
        val dataFromDb = populateDataFromDb()
        result.addSource(dataFromDb) {
            if (shouldFetch(it)) {
                fetchDataFromNetwork(dataFromDb)
            } else {
                result.postValue(Resource.success(it))
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
            when (response.status) {
                StatusResponse.SUCCESS -> {
                    saveCallResult(response.body as RequestType)
                    result.addSource(populateDataFromDb()) { dbData ->
                        if (dbData != null) {
                            result.postValue(Resource.success(dbData))
                        }
                    }
                }

                StatusResponse.EMPTY -> {
                    result.addSource(populateDataFromDb()) { dbData ->
                        result.postValue(Resource.success(dbData))
                    }
                }
                StatusResponse.ERROR -> {
                    result.addSource(dataFromDb) { newData ->
                        result.postValue(Resource.error(response.message, newData))
                    }
                }
            }
        }
    }
}