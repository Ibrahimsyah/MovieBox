package com.zairussalamdev.moviebox.data

import com.zairussalamdev.moviebox.data.entities.DetailEntity
import com.zairussalamdev.moviebox.data.entities.MovieEntity

interface MovieDataSource {
    suspend fun getMovieList(): List<MovieEntity>

    suspend fun getTvShowsList(): List<MovieEntity>

    suspend fun getMovieDetail(id: Int): DetailEntity

    suspend fun getTvShowDetail(id: Int): DetailEntity
}