package com.zairussalamdev.moviebox.utils

import com.zairussalamdev.moviebox.data.local.entities.DetailEntity
import com.zairussalamdev.moviebox.data.local.entities.MovieEntity
import com.zairussalamdev.moviebox.data.remote.responses.DetailResponse
import com.zairussalamdev.moviebox.data.remote.responses.Genre

object DummyData {
    fun getDummyListData(): List<MovieEntity> {
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

    fun getDummyDetailData(): DetailEntity {
        return DetailEntity(
            id = 1,
            title = "name1",
            overview = "overview1",
            posterPath = "poster1",
            voteAverage = 5.0,
            popularity = 123123.0,
            tagLine = "tagLine",
            homepage = "homepage",
            status = "status"
        )
    }

    fun getDummyMovieDetailResponse(): DetailResponse {
        return DetailResponse(
                "overview",
                "title",
                "poster",
                9.9,
                "tagline",
                listOf(Genre("genre1")),
                1,
                "homepage",
                "release",
                12.0,
        )
    }

    fun getDummyTvShowDetailResponse(): DetailResponse {
        return DetailResponse(
                "overview",
                "title",
                "poster",
                9.9,
                "tagline",
                listOf(Genre("genre1")),
                1,
                "homepage",
                "release",
                12.0,
        )
    }
}