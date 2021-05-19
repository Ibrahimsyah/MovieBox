package com.zairussalamdev.moviebox.core.utils

import com.zairussalamdev.moviebox.core.configs.Constants
import com.zairussalamdev.moviebox.core.data.source.local.entities.DetailEntity
import com.zairussalamdev.moviebox.core.data.source.local.entities.MovieEntity
import com.zairussalamdev.moviebox.core.data.source.remote.responses.DetailResponse
import com.zairussalamdev.moviebox.core.data.source.remote.responses.ListMovieResponse
import com.zairussalamdev.moviebox.core.data.source.remote.responses.ListTvShowResponse
import com.zairussalamdev.moviebox.core.domain.model.Detail
import com.zairussalamdev.moviebox.core.domain.model.Genre
import com.zairussalamdev.moviebox.core.domain.model.Movie

object Mapper {
    fun movieEntitiesToDomain(list: List<MovieEntity>): List<Movie> {
        return list.map {
            Movie(
                it.id,
                it.overview,
                it.title,
                it.posterPath,
                it.voteAverage,
                it.movieType,
                it.isFavorite
            )
        }
    }

    fun movieResponsesToEntities(responseList: ListMovieResponse): List<MovieEntity> {
        val movies = responseList.movieResponses
        return movies.map {
            MovieEntity(
                it.id,
                it.overview,
                it.title,
                it.posterPath,
                it.voteAverage,
                Constants.TYPE_MOVIE,
            )
        }
    }

    fun tvShowResponsesToEntities(responseList: ListTvShowResponse): List<MovieEntity> {
        val movies = responseList.tvShowResponses
        return movies.map {
            MovieEntity(
                it.id,
                it.overview,
                it.name,
                it.posterPath,
                it.voteAverage,
                Constants.TYPE_TV_SHOW,
            )
        }
    }

    fun detailEntityToDomain(entity: DetailEntity?): Detail? {
        if (entity == null) return null
        val genres = entity.genres.map { Genre(it) }
        return Detail(
            entity.overview,
            entity.title,
            entity.posterPath,
            entity.voteAverage,
            entity.tagLine,
            genres,
            entity.id,
            entity.homepage,
            entity.status,
            entity.popularity,
        )
    }

    fun detailResponseToEntity(response: DetailResponse, movieType: Int): DetailEntity {
        val genres = response.genreResponses.map { it.name }
        return DetailEntity(
            response.id,
            response.overview,
            response.title,
            response.posterPath,
            response.voteAverage,
            response.popularity,
            response.tagLine,
            genres,
            response.homepage,
            response.status,
            movieType
        )
    }
}