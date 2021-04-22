package com.zairussalamdev.moviebox.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zairussalamdev.moviebox.configs.Constants
import com.zairussalamdev.moviebox.data.TMDBRepository
import com.zairussalamdev.moviebox.data.local.entities.DetailEntity
import com.zairussalamdev.moviebox.data.local.entities.MovieEntity
import com.zairussalamdev.moviebox.utils.ErrorMessageHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val repository: TMDBRepository) : ViewModel() {
    private var movieType = Constants.TYPE_MOVIE
    private val detailEntity = MutableLiveData<DetailEntity>()
    private val loading = MutableLiveData<Boolean>()
    private val errorMessage = MutableLiveData<String>()

    fun getLoading(): LiveData<Boolean> = loading
    fun getErrorMessage(): LiveData<String> = errorMessage

    fun getMovieDetail(id: Int): LiveData<DetailEntity> {
        movieType = Constants.TYPE_MOVIE
        viewModelScope.launch {
            loading.postValue(true)
            try {
                val detail = repository.getMovieDetail(id)
                detailEntity.postValue(detail)
            } catch (error: Exception) {
                val message = ErrorMessageHandler.generateErrorMessage(error)
                errorMessage.postValue(message)
            }
            loading.postValue(false)
        }
        return detailEntity
    }

    fun getTvShowDetail(id: Int): LiveData<DetailEntity> {
        movieType = Constants.TYPE_TV_SHOW
        viewModelScope.launch {
            loading.postValue(true)
            try {
                val detail = repository.getTvShowDetail(id)
                detailEntity.postValue(detail)
            } catch (error: Exception) {
                val message = ErrorMessageHandler.generateErrorMessage(error)
                errorMessage.postValue(message)
            }
            loading.postValue(false)
        }
        return detailEntity
    }

    fun checkIsMovieFavorite(id: Int): LiveData<Boolean> {
        return repository.checkMovieFavorite(id)
    }

    fun addMovieToFavorite(movieEntity: MovieEntity) {
        viewModelScope.launch {
            repository.insertFavoriteMovie(movieEntity)
        }
    }

    fun deleteMovieFromFavorite(movieEntity: MovieEntity) {
        viewModelScope.launch {
            repository.deleteFavoriteMovie(movieEntity)
        }
    }
}