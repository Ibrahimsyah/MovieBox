package com.zairussalamdev.moviebox.ui.movies

import androidx.lifecycle.ViewModel
import com.zairussalamdev.moviebox.core.data.TMDBRepository

class MovieViewModel constructor(private val repository: TMDBRepository) : ViewModel() {
    fun getMovieList() = repository.getMovieList()

    fun getTvShowsList() = repository.getTvShowsList()
}