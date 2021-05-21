package com.zairussalamdev.moviebox.favorite.di

import com.zairussalamdev.moviebox.favorite.favoritemovies.FavoriteMoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    viewModel { FavoriteMoviesViewModel(get()) }
}