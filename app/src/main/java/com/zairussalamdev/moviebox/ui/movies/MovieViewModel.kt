package com.zairussalamdev.moviebox.ui.movies

import androidx.lifecycle.ViewModel
import com.zairussalamdev.moviebox.data.TMDBRepository
import javax.inject.Inject

class MovieViewModel @Inject constructor(private val repository: TMDBRepository) : ViewModel() {
    fun getMovieList() = repository.getMovieList()

    fun getTvShowsList() = repository.getTvShowsList()
}