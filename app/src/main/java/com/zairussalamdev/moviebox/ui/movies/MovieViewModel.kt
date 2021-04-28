package com.zairussalamdev.moviebox.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.zairussalamdev.moviebox.data.TMDBRepository
import com.zairussalamdev.moviebox.data.local.entities.MovieEntity
import com.zairussalamdev.moviebox.vo.Resource
import javax.inject.Inject

class MovieViewModel @Inject constructor(private val repository: TMDBRepository) : ViewModel() {
    fun getMovieList(): LiveData<Resource<PagedList<MovieEntity>>> {
        return repository.getMovieList()
    }

    fun getTvShowsList(): LiveData<Resource<PagedList<MovieEntity>>> {
        return repository.getTvShowsList()
    }
}