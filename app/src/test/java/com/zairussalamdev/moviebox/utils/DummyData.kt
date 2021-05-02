package com.zairussalamdev.moviebox.utils

import com.zairussalamdev.moviebox.data.local.entities.DetailEntity
import com.zairussalamdev.moviebox.data.local.entities.MovieEntity

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
}