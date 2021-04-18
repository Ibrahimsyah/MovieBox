package com.zairussalamdev.moviebox.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zairussalamdev.moviebox.data.TMDBRepository
import com.zairussalamdev.moviebox.data.entities.DetailEntity
import com.zairussalamdev.moviebox.utils.ErrorMessageHandler
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: TMDBRepository) : ViewModel() {
    private val loading = MutableLiveData<Boolean>()
    private val errorMessage = MutableLiveData<String>()

    fun getLoading(): LiveData<Boolean> = loading
    fun getErrorMessage(): LiveData<String> = errorMessage

    fun getMovieDetail(id: Int): LiveData<DetailEntity> {
        val result = MutableLiveData<DetailEntity>()
        viewModelScope.launch {
            loading.postValue(true)
            try {
                val detail = repository.getMovieDetail(id)
                result.postValue(detail)
            } catch (error: Exception) {
                val message = ErrorMessageHandler.generateErrorMessage(error)
                errorMessage.postValue(message)
            }
            loading.postValue(false)
        }
        return result
    }

    fun getTvShowDetail(id: Int): LiveData<DetailEntity> {
        val result = MutableLiveData<DetailEntity>()
        viewModelScope.launch {
            loading.postValue(true)
            try {
                val detail = repository.getTvShowDetail(id)
                result.postValue(detail)
            } catch (error: Exception) {
                val message = ErrorMessageHandler.generateErrorMessage(error)
                errorMessage.postValue(message)
            }
            loading.postValue(false)
        }
        return result
    }
}