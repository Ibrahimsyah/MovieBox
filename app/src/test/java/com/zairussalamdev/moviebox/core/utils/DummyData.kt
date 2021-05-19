package com.zairussalamdev.moviebox.core.utils

import com.zairussalamdev.moviebox.core.data.source.local.entities.DetailEntity
import com.zairussalamdev.moviebox.core.data.source.local.entities.MovieEntity
import com.zairussalamdev.moviebox.core.domain.model.Detail
import com.zairussalamdev.moviebox.core.domain.model.Movie

object DummyData {
    fun getDummyListEntityData(): List<MovieEntity> {
        val result = ArrayList<MovieEntity>()
        result.add(
            MovieEntity(
                id = 1,
                title = "name1",
                overview = "overview1",
                posterPath = "poster1",
                voteAverage = 5.0,
            )
        )
        result.add(
            MovieEntity(
                overview = "overview2",
                title = "name2",
                posterPath = "poster2",
                voteAverage = 5.0,
                id = 2,
            ),
        )
        return result
    }

    fun getDummyListData(): List<Movie> {
        val result = ArrayList<Movie>()
        result.add(
            Movie(
                id = 1,
                title = "name1",
                overview = "overview1",
                posterPath = "poster1",
                voteAverage = 5.0,
                movieType = 1
            )
        )
        result.add(
            Movie(
                overview = "overview2",
                title = "name2",
                posterPath = "poster2",
                voteAverage = 5.0,
                id = 2,
                movieType = 1
            ),
        )
        return result
    }

    fun getDummyDetailEntityData(): DetailEntity {
        return DetailEntity(
            id = 1,
            title = "name1",
            overview = "overview1",
            posterPath = "poster1",
            voteAverage = 5.0,
            popularity = 123123.0,
            tagLine = "tagLine",
            homepage = "homepage",
            status = "status",
            genres = listOf()
        )
    }

    fun getDummyDetailData(): Detail {
        return Detail(
            id = 1,
            title = "name1",
            overview = "overview1",
            posterPath = "poster1",
            voteAverage = 5.0,
            popularity = 123123.0,
            tagLine = "tagLine",
            homepage = "homepage",
            status = "status",
            genres = listOf()
        )
    }
}