package com.zairussalamdev.moviebox.core.di

import com.zairussalamdev.moviebox.ui.detail.DetailViewModel
import com.zairussalamdev.moviebox.ui.favoritemovies.FavoriteMoviesViewModel
import com.zairussalamdev.moviebox.ui.movies.MovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { FavoriteMoviesViewModel(get()) }
}