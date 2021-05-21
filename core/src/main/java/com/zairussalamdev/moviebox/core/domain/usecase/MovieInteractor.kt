package com.zairussalamdev.moviebox.core.domain.usecase

import androidx.lifecycle.asLiveData
import com.zairussalamdev.moviebox.core.domain.repository.ITMDBRepository

class MovieInteractor(private val repository: ITMDBRepository) : MovieUseCase {
    override fun getAllMovies() = repository.getMovieList().asLiveData()

    override fun getAllTvShows() = repository.getTvShowsList().asLiveData()

    override fun getMovieDetail(movieId: Int) = repository.getMovieDetail(movieId).asLiveData()

    override fun getTvShowDetail(tvShowId: Int) = repository.getTvShowDetail(tvShowId).asLiveData()

    override fun getFavoriteMovies() = repository.getFavoriteMovies().asLiveData()

    override fun getFavoriteTvShows() = repository.getFavoriteTvShows().asLiveData()

    override fun checkMovieFavorite(movieId: Int) =
        repository.checkMovieFavorite(movieId).asLiveData()

    override suspend fun addMovieToFavorite(movieId: Int) {
        repository.insertFavoriteMovie(movieId)
    }

    override suspend fun removeMovieFromFavorite(movieId: Int) {
        repository.deleteFavoriteMovie(movieId)
    }
}