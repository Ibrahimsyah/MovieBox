package com.zairussalamdev.moviebox.ui.movies

import androidx.lifecycle.ViewModel
import com.zairussalamdev.moviebox.core.domain.usecase.MovieUseCase

class MovieViewModel constructor(private val useCase: MovieUseCase) : ViewModel() {
    fun getMovieList() = useCase.getAllMovies()
    fun getTvShowsList() = useCase.getAllTvShows()
}