package com.zairussalamdev.moviebox.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.zairussalamdev.moviebox.configs.Constants
import com.zairussalamdev.moviebox.data.local.LocalDataSource
import com.zairussalamdev.moviebox.data.local.entities.DetailEntity
import com.zairussalamdev.moviebox.data.local.entities.MovieEntity
import com.zairussalamdev.moviebox.data.remote.NetworkBoundResource
import com.zairussalamdev.moviebox.data.remote.RemoteDataSource
import com.zairussalamdev.moviebox.data.remote.responses.MovieResponse
import com.zairussalamdev.moviebox.data.remote.responses.TvShowResponse
import com.zairussalamdev.moviebox.vo.Resource
import javax.inject.Inject

class TMDBRepository @Inject constructor(
        private val remoteDataSource: RemoteDataSource,
        private val localDataSource: LocalDataSource
) : MovieDataSource {
    override fun getMovieList(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieEntity>, MovieResponse>() {
            override fun populateDataFromDb() = localDataSource.getMovies().toLiveData(4)

            override fun shouldFetch(data: PagedList<MovieEntity>?) = data.isNullOrEmpty()

            override suspend fun networkCall() = remoteDataSource.getMovieList()

            override suspend fun saveCallResult(data: MovieResponse) {
                data.movies.forEach { movie ->
                    val entity = MovieEntity(
                        overview = movie.overview,
                        title = movie.title,
                        posterPath = movie.posterPath,
                        voteAverage = movie.voteAverage,
                        movieType = Constants.TYPE_MOVIE,
                        id = movie.id
                    )
                    localDataSource.insertMovie(entity)
                }
            }
        }.build()
    }

    override fun getTvShowsList(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieEntity>, TvShowResponse>() {
            override fun populateDataFromDb() = localDataSource.getTvShows().toLiveData(4)

            override fun shouldFetch(data: PagedList<MovieEntity>?) = data.isNullOrEmpty()

            override suspend fun networkCall() = remoteDataSource.getTvShowList()

            override suspend fun saveCallResult(data: TvShowResponse) {
                data.tvShows.forEach { movie ->
                    val entity = MovieEntity(
                        overview = movie.overview,
                        title = movie.name,
                        posterPath = movie.posterPath,
                        voteAverage = movie.voteAverage,
                        movieType = Constants.TYPE_TV_SHOW,
                        id = movie.id
                    )
                    localDataSource.insertMovie(entity)
                }
            }
        }.build()
    }

    override suspend fun getMovieDetail(id: Int): DetailEntity {
        val response = remoteDataSource.getMovieDetail(id)
        val genres = response.genres.map { genre -> genre.name }
        return DetailEntity(
                response.overview,
                response.title,
                response.posterPath,
                response.voteAverage,
                response.popularity,
                response.tagLine,
                genres = genres,
                response.id,
                response.homepage,
                response.status
        )
    }

    override suspend fun getTvShowDetail(id: Int): DetailEntity {
        val response = remoteDataSource.getTvShowDetail(id)
        val genres = response.genres.map { genre -> genre.name }
        return DetailEntity(
            response.overview,
            response.title,
            response.posterPath,
            response.voteAverage,
            response.popularity,
            response.tagLine,
            genres = genres,
            response.id,
            response.homepage,
            response.status
        )
    }

    override fun getFavoriteMovies(): LiveData<PagedList<MovieEntity>> {
        return localDataSource.getFavoriteMovies().toLiveData(4)
    }

    override fun getFavoriteTvShows(): LiveData<PagedList<MovieEntity>> {
        return localDataSource.getFavoriteTvShows().toLiveData(4)
    }

    override fun checkMovieFavorite(id: Int): LiveData<Boolean> {
        return localDataSource.checkMovieFavorite(id)
    }

    override suspend fun insertFavoriteMovie(id: Int) {
        localDataSource.addFavoriteMovie(id)
    }

    override suspend fun deleteFavoriteMovie(id: Int) {
        localDataSource.deleteFavoriteMovie(id)
    }
}