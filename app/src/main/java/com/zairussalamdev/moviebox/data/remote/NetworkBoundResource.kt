package com.zairussalamdev.moviebox.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.zairussalamdev.moviebox.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

abstract class NetworkBoundResource<ResultType, RequestType> {
    private val result = MediatorLiveData<Resource<ResultType>>()

    fun build(): LiveData<Resource<ResultType>> {
        result.postValue(Resource.loading(null))
        val dataFromDb = populateDataFromDb()
        result.addSource(dataFromDb) {
            if (shouldFetch(it)) {
                fetchDataFromNetwork(dataFromDb)
            } else {
                result.postValue(Resource.success(it))
            }
        }
        return result
    }

    protected abstract fun populateDataFromDb(): LiveData<ResultType>
    protected abstract fun shouldFetch(data: ResultType?): Boolean
    protected abstract suspend fun networkCall(): LiveData<ApiResponse<RequestType>>
    protected abstract suspend fun saveCallResult(data: RequestType)
    private fun fetchDataFromNetwork(dataFromDb: LiveData<ResultType>) {
        val apiResponse = runBlocking { networkCall() }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dataFromDb)
            when (response.status) {
                StatusResponse.SUCCESS -> {
                    runBlocking(Dispatchers.IO) { saveCallResult(response.body) }
                    result.addSource(populateDataFromDb()) { dbData ->
                        result.postValue(Resource.success(dbData))
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