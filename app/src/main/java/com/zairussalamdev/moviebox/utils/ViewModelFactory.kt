package com.zairussalamdev.moviebox.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zairussalamdev.moviebox.data.TMDBRepository
import com.zairussalamdev.moviebox.di.Injection
import com.zairussalamdev.moviebox.ui.detail.DetailViewModel
import com.zairussalamdev.moviebox.ui.movies.MovieViewModel

class ViewModelFactory(private val repository: TMDBRepository) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        private var instance: ViewModelFactory? = null

        fun getInstance(): ViewModelFactory {
            val repository = Injection.provideRepository()
            if (instance == null) {
                synchronized(this) {
                    instance = ViewModelFactory(repository)
                }
            }
            return instance as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> MovieViewModel(repository) as T
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel(repository) as T
            else -> MovieViewModel(repository) as T
        }
    }
}