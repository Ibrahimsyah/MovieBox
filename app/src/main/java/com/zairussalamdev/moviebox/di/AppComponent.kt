package com.zairussalamdev.moviebox.di

import com.zairussalamdev.moviebox.ui.detail.DetailActivity
import com.zairussalamdev.moviebox.ui.favoritemovies.FavoriteMoviesFragment
import com.zairussalamdev.moviebox.ui.movies.MovieFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, StorageModule::class])
interface AppComponent {
    fun inject(favoriteMoviesFragment: FavoriteMoviesFragment)
    fun inject(movieFragment: MovieFragment)
    fun inject(detailActivity: DetailActivity)
}