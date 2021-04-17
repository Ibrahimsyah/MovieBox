package com.zairussalamdev.moviebox.utils

import com.zairussalamdev.moviebox.data.entities.DetailEntity
import com.zairussalamdev.moviebox.data.entities.MovieEntity
import com.zairussalamdev.moviebox.data.responses.*

object DummyData {
    fun getDummyListData(): List<MovieEntity> {
        val result = ArrayList<MovieEntity>()
        result.add(
            MovieEntity(
                overview = "overview1",
                title = "name1",
                posterPath = "poster1",
                voteAverage = 5.0,
                id = 1,
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
            title = "title",
            overview = "overview",
            posterPath = "posterPath",
            voteAverage = 5.0,
            tagLine = "tagLine",
            homepage = "homepage",
            status = "status"
        )
    }

    fun getDummyMovieResponse(): MovieResponse {
        return MovieResponse(
            movies = mutableListOf(
                Movie("overview1", "title1", "poster1", 5.6, 1),
                Movie("overview2", "title2", "poster2", 5.6, 2),
                Movie("overview3", "title3", "poster3", 5.6, 3),
            )
        )

    }

    fun getDummyMovieDetailResponse(): DetailResponse {
        return DetailResponse(
            "overview",
            "title",
            "poster",
            5.6,
            "tagLine",
            1,
            "homepage",
            "status",
        )
    }

    fun getDummyTvShowResponse(): TvShowResponse {
        return TvShowResponse(
            tvShows = mutableListOf(
                TvShow("overview1", "poster1", 5.0, "name1", 1),
                TvShow("overview2", "poster2", 4.0, "name2", 2),
                TvShow("overview3", "poster3", 5.0, "name3", 3),
            )
        )

    }

    fun getDummyTvShowDetailResponse(): DetailResponse {
        return DetailResponse(
            overview = "overview",
            title = "title",
            posterPath = "poster",
            id = 1,
            voteAverage = 5.0,
            tagLine = "tagLine",
            homepage = "homepage",
            status = "status"
        )
    }

}