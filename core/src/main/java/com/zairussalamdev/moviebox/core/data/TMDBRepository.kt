package com.zairussalamdev.moviebox.core.data

import com.zairussalamdev.moviebox.core.configs.Constants
import com.zairussalamdev.moviebox.core.data.source.local.LocalDataSource
import com.zairussalamdev.moviebox.core.data.source.remote.RemoteDataSource
import com.zairussalamdev.moviebox.core.data.source.remote.network.ApiResponse
import com.zairussalamdev.moviebox.core.data.source.remote.responses.DetailResponse
import com.zairussalamdev.moviebox.core.data.source.remote.responses.ListMovieResponse
import com.zairussalamdev.moviebox.core.data.source.remote.responses.ListTvShowResponse
import com.zairussalamdev.moviebox.core.domain.model.Detail
import com.zairussalamdev.moviebox.core.domain.model.Movie
import com.zairussalamdev.moviebox.core.domain.repository.ITMDBRepository
import com.zairussalamdev.moviebox.core.utils.Mapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull

class TMDBRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : ITMDBRepository {
    override fun getMovieList(): Flow<Resource<List<Movie>>> {
        return object : com.zairussalamdev.moviebox.core.data.NetworkBoundResource<List<Movie>, ListMovieResponse>() {
            override fun populateDataFromDb(): Flow<List<Movie>> {
                return localDataSource.getMovies().map { Mapper.movieEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<Movie>?) = data.isNullOrEmpty()

            override suspend fun networkCall(): Flow<ApiResponse<ListMovieResponse>> {
                return remoteDataSource.getMovieList()
            }

            override suspend fun saveCallResult(data: ListMovieResponse) {
                val mappedData = Mapper.movieResponsesToEntities(data)
                localDataSource.insertMovie(mappedData)
            }
        }.build()
    }

    override fun getTvShowsList(): Flow<Resource<List<Movie>>> {
        return object : com.zairussalamdev.moviebox.core.data.NetworkBoundResource<List<Movie>, ListTvShowResponse>() {
            override fun populateDataFromDb(): Flow<List<Movie>> {
                return localDataSource.getTvShows().map {
                    Mapper.movieEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?) = data.isNullOrEmpty()

            override suspend fun networkCall(): Flow<ApiResponse<ListTvShowResponse>> {
                return remoteDataSource.getTvShowList()
            }

            override suspend fun saveCallResult(data: ListTvShowResponse) {
                val mapped = Mapper.tvShowResponsesToEntities(data)
                localDataSource.insertMovie(mapped)
            }
        }.build()
    }

    override fun getMovieDetail(id: Int): Flow<Resource<Detail>> {
        return object : com.zairussalamdev.moviebox.core.data.NetworkBoundResource<Detail, DetailResponse>() {
            override fun populateDataFromDb(): Flow<Detail> {
                return localDataSource.getMovieDetail(id).mapNotNull {
                    if (it == null) {
                        Detail.createEmptyObject()
                    } else {
                        Mapper.detailEntityToDomain(it)
                    }
                }
            }

            override fun shouldFetch(data: Detail?) = data?.id == -1

            override suspend fun networkCall(): Flow<ApiResponse<DetailResponse>> {
                return remoteDataSource.getMovieDetail(id)
            }

            override suspend fun saveCallResult(data: DetailResponse) {
                val detail = Mapper.detailResponseToEntity(data, Constants.TYPE_MOVIE)
                localDataSource.insertDetailMovie(detail)
            }
        }.build()
    }

    override fun getTvShowDetail(id: Int): Flow<Resource<Detail>> {
        return object : com.zairussalamdev.moviebox.core.data.NetworkBoundResource<Detail, DetailResponse>() {
            override fun populateDataFromDb(): Flow<Detail> {
                return localDataSource.getTvShowDetail(id).map {
                    if (it == null) {
                        Detail.createEmptyObject()
                    } else {
                        Mapper.detailEntityToDomain(it)
                    }
                }
            }

            override fun shouldFetch(data: Detail?) = data?.id == -1

            override suspend fun networkCall(): Flow<ApiResponse<DetailResponse>> {
                return remoteDataSource.getTvShowDetail(id)
            }

            override suspend fun saveCallResult(data: DetailResponse) {
                val detail = Mapper.detailResponseToEntity(data, Constants.TYPE_TV_SHOW)
                localDataSource.insertDetailMovie(detail)
            }

        }.build()
    }

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return localDataSource.getFavoriteMovies().map {
            Mapper.movieEntitiesToDomain(it)
        }
    }

    override fun getFavoriteTvShows(): Flow<List<Movie>> {
        return localDataSource.getFavoriteTvShows().map {
            Mapper.movieEntitiesToDomain(it)
        }
    }

    override fun checkMovieFavorite(id: Int): Flow<Boolean> {
        return localDataSource.checkMovieFavorite(id)
    }

    override suspend fun insertFavoriteMovie(id: Int) {
        localDataSource.addFavoriteMovie(id)
    }

    override suspend fun deleteFavoriteMovie(id: Int) {
        localDataSource.deleteFavoriteMovie(id)
    }
}