package com.zairussalamdev.moviebox.utils

import com.zairussalamdev.moviebox.data.entities.DetailEntity
import com.zairussalamdev.moviebox.data.entities.MovieEntity

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
}