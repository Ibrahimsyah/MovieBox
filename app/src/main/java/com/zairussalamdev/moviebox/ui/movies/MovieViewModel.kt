package com.zairussalamdev.moviebox.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zairussalamdev.moviebox.data.TMDBRepository
import com.zairussalamdev.moviebox.data.entities.MovieEntity
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: TMDBRepository) : ViewModel() {
    private val loading = MutableLiveData<Boolean>()
    private val errorMessage = MutableLiveData<String>()

    fun getLoading(): LiveData<Boolean> = loading
    fun getErrorMessage(): LiveData<String> = errorMessage

    fun getMovieList(): LiveData<List<MovieEntity>> {
        val result = MutableLiveData<List<MovieEntity>>()
        viewModelScope.launch {
            loading.postValue(true)
            try {
                val movies = repository.getMovieList()
                if (movies.isEmpty()) {
                    errorMessage.postValue("No Data Found")
                }
                result.postValue(movies)
            } catch (error: Exception) {
                errorMessage.postValue(error.message)
            }
            loading.postValue(false)
        }
        return result
    }

    fun getTvShowsList(): LiveData<List<MovieEntity>> {
        val result = MutableLiveData<List<MovieEntity>>()
        viewModelScope.launch {
            loading.postValue(true)
            try {
                val movies = repository.getTvShowsList()
                if (movies.isEmpty()) {
                    errorMessage.postValue("No Data Found")
                }
                result.postValue(movies)
            } catch (error: Exception) {
                errorMessage.postValue(error.message)
            }
            loading.postValue(false)
        }
        return result
    }
}