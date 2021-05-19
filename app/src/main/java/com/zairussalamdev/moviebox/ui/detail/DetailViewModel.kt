package com.zairussalamdev.moviebox.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zairussalamdev.moviebox.core.domain.model.Movie
import com.zairussalamdev.moviebox.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.launch

class DetailViewModel constructor(private val useCase: MovieUseCase) : ViewModel() {

    fun getMovieDetail(id: Int) = useCase.getMovieDetail(id)

    fun getTvShowDetail(id: Int) = useCase.getTvShowDetail(id)

    fun checkIsMovieFavorite(id: Int): LiveData<Boolean> {
        return useCase.checkMovieFavorite(id)
    }

    fun addMovieToFavorite(movie: Movie) {
        viewModelScope.launch {
            useCase.addMovieToFavorite(movie.id)
        }
    }

    fun deleteMovieFromFavorite(movie: Movie) {
        viewModelScope.launch {
            useCase.addMovieToFavorite(movie.id)
        }
    }
}