package com.zairussalamdev.moviebox.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TMDBRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : ITMDBRepository {
    override fun getMovieList(): LiveData<Resource<List<Movie>>> {
        return object : NetworkBoundResource<List<Movie>, ListMovieResponse>() {
            override fun populateDataFromDb(): LiveData<List<Movie>> {
                return Transformations.map(localDataSource.getMovies()) {
                    Mapper.movieEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?) = data.isNullOrEmpty()

            override fun networkCall(): LiveData<ApiResponse<ListMovieResponse>> {
                val result = MutableLiveData<ApiResponse<ListMovieResponse>>()
                CoroutineScope(Dispatchers.Main).launch {
                    remoteDataSource.getMovieList().observeForever {
                        result.postValue(it)
                    }
                }
                return result
            }

            override fun saveCallResult(data: ListMovieResponse) {
                CoroutineScope(Dispatchers.Main).launch {
                    val mappedData = Mapper.movieResponsesToEntities(data)
                    launch { localDataSource.insertMovie(mappedData) }
                }
            }
        }.asLiveData()
    }

    override fun getTvShowsList(): LiveData<Resource<List<Movie>>> {
        return object : NetworkBoundResource<List<Movie>, ListTvShowResponse>() {
            override fun populateDataFromDb(): LiveData<List<Movie>> {
                return Transformations.map(localDataSource.getTvShows()) {
                    Mapper.movieEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?) = data.isNullOrEmpty()

            override fun networkCall(): LiveData<ApiResponse<ListTvShowResponse>> {
                val result = MutableLiveData<ApiResponse<ListTvShowResponse>>()
                CoroutineScope(Dispatchers.Main).launch {
                    remoteDataSource.getTvShowList().observeForever {
                        result.postValue(it)
                    }
                }
                return result
            }

            override fun saveCallResult(data: ListTvShowResponse) {
                CoroutineScope(Dispatchers.Main).launch {
                    val mapped = Mapper.tvShowResponsesToEntities(data)
                    localDataSource.insertMovie(mapped)
                }
            }
        }.asLiveData()
    }

    override fun getMovieDetail(id: Int): LiveData<Resource<Detail>> {
        return object : NetworkBoundResource<Detail, DetailResponse>() {
            override fun populateDataFromDb(): LiveData<Detail> {
                return Transformations.map(localDataSource.getMovieDetail(id)) {
                    Mapper.detailEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: Detail?) = data == null

            override fun networkCall(): LiveData<ApiResponse<DetailResponse>> {
                val result = MutableLiveData<ApiResponse<DetailResponse>>()
                CoroutineScope(Dispatchers.Main).launch {
                    remoteDataSource.getMovieDetail(id).observeForever {
                        result.postValue(it)
                    }
                }
                return result
            }

            override fun saveCallResult(data: DetailResponse) {
                val detail = Mapper.detailResponseToEntity(data, Constants.TYPE_MOVIE)
                CoroutineScope(Dispatchers.Main).launch {
                    localDataSource.insertDetailMovie(detail)
                }
            }
        }.asLiveData()
    }

    override fun getTvShowDetail(id: Int): LiveData<Resource<Detail>> {
        return object : NetworkBoundResource<Detail, DetailResponse>() {
            override fun populateDataFromDb(): LiveData<Detail> {
                return Transformations.map(localDataSource.getTvShowDetail(id)) {
                    Mapper.detailEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: Detail?) = data == null

            override fun networkCall(): LiveData<ApiResponse<DetailResponse>> {
                val result = MutableLiveData<ApiResponse<DetailResponse>>()
                CoroutineScope(Dispatchers.Main).launch {
                    remoteDataSource.getTvShowDetail(id).observeForever {
                        result.postValue(it)
                    }
                }
                return result
            }

            override fun saveCallResult(data: DetailResponse) {
                val detail = Mapper.detailResponseToEntity(data, Constants.TYPE_TV_SHOW)
                CoroutineScope(Dispatchers.Main).launch {
                    localDataSource.insertDetailMovie(detail)
                }
            }

        }.asLiveData()
    }

    override fun getFavoriteMovies(): LiveData<List<Movie>> {
        return Transformations.map(localDataSource.getFavoriteMovies()) {
            Mapper.movieEntitiesToDomain(it)

        }
    }

    override fun getFavoriteTvShows(): LiveData<List<Movie>> {
        return Transformations.map(localDataSource.getFavoriteTvShows()) {
            Mapper.movieEntitiesToDomain(it)
        }
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