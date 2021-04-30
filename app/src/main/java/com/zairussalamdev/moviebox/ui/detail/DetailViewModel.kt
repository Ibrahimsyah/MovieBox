package com.zairussalamdev.moviebox.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zairussalamdev.moviebox.data.TMDBRepository
import com.zairussalamdev.moviebox.data.local.entities.MovieEntity
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val repository: TMDBRepository) : ViewModel() {

    fun getMovieDetail(id: Int) = repository.getMovieDetail(id)

    fun getTvShowDetail(id: Int) = repository.getTvShowDetail(id)

    fun checkIsMovieFavorite(id: Int): LiveData<Boolean> {
        return repository.checkMovieFavorite(id)
    }

    fun addMovieToFavorite(movieEntity: MovieEntity) {
        viewModelScope.launch {
            repository.insertFavoriteMovie(movieEntity.id)
        }
    }

    fun deleteMovieFromFavorite(movieEntity: MovieEntity) {
        viewModelScope.launch {
            repository.deleteFavoriteMovie(movieEntity.id)
        }
    }
}