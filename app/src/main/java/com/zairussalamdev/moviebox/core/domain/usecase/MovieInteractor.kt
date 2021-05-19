package com.zairussalamdev.moviebox.core.domain.usecase

import com.zairussalamdev.moviebox.core.domain.repository.ITMDBRepository

class MovieInteractor(private val repository: ITMDBRepository) : MovieUseCase {
    override fun getAllMovies() = repository.getMovieList()

    override fun getAllTvShows() = repository.getTvShowsList()

    override fun getMovieDetail(movieId: Int) = repository.getMovieDetail(movieId)

    override fun getTvShowDetail(tvShowId: Int) = repository.getTvShowDetail(tvShowId)

    override fun getFavoriteMovies() = repository.getFavoriteMovies()

    override fun getFavoriteTvShows() = repository.getFavoriteTvShows()

    override fun checkMovieFavorite(movieId: Int) = repository.checkMovieFavorite(movieId)

    override suspend fun addMovieToFavorite(movieId: Int) {
        repository.insertFavoriteMovie(movieId)
    }

    override suspend fun removeMovieFromFavorite(movieId: Int) {
        repository.deleteFavoriteMovie(movieId)
    }
}