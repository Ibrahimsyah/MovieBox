package com.zairussalamdev.moviebox.data.remote.responses

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter

@ProvidedTypeConverter
class GenreConverter {

    @TypeConverter
    fun genreListFromString(value: String?): List<String>? {
        return value?.let {
            val genres = it.split(',')
            return genres.map { genre -> genre }
        }
    }

    @TypeConverter
    fun stringFromGenres(genres: List<String>?): String? {
        return genres?.joinToString { genre -> genre }
    }

}