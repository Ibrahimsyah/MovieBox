package com.zairussalamdev.moviebox.services.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {
    companion object {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
        private var instance: Retrofit? = null
        fun getInstance(): Retrofit {
            if (instance == null) {
                instance = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return instance as Retrofit
        }
    }
}