package com.zairussalamdev.moviebox.favorite.favoritemovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.zairussalamdev.moviebox.core.domain.model.Movie
import com.zairussalamdev.moviebox.core.domain.usecase.MovieUseCase

class FavoriteMoviesViewModel constructor(private val useCase: MovieUseCase) : ViewModel() {
    private val errorMessage = MutableLiveData<String>()

    fun getErrorMessage(): LiveData<String> = errorMessage

    fun getFavoriteMovieList(): LiveData<List<Movie>> {
        return Transformations.map(useCase.getFavoriteMovies()) {
            val message = if (it.isEmpty()) "No Data" else ""
            errorMessage.postValue(message)
            it
        }
    }

    fun getFavoriteTvShowList(): LiveData<List<Movie>> {
        return Transformations.map(useCase.getFavoriteTvShows()) {
            val message = if (it.isEmpty()) "No Data" else ""
            errorMessage.postValue(message)
            it
        }
    }
}