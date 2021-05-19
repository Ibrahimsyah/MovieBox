package com.zairussalamdev.moviebox.ui.favoritemovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.zairussalamdev.moviebox.core.data.TMDBRepository
import com.zairussalamdev.moviebox.core.data.source.local.entities.MovieEntity

class FavoriteMoviesViewModel constructor(private val repository: TMDBRepository) : ViewModel() {
    private val errorMessage = MutableLiveData<String>()

    fun getErrorMessage(): LiveData<String> = errorMessage

    fun getFavoriteMovieList(): LiveData<PagedList<MovieEntity>> {
        val result = repository.getFavoriteMovies()
        result.observeForever {
            val message = if (it.isEmpty()) "No Data" else ""
            errorMessage.postValue(message)
        }
        return result
    }

    fun getFavoriteTvShowList(): LiveData<PagedList<MovieEntity>> {
        val result = repository.getFavoriteTvShows()
        result.observeForever {
            val message = if (it.isEmpty()) "No Data" else ""
            errorMessage.postValue(message)
        }
        return result
    }
}