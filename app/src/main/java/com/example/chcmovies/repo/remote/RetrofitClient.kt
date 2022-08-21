package com.example.chcmovies.repo.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    const val BASE_URL = "https://raw.githubusercontent.com/theapache64/top250/master/"

    val instance: RetrofitApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ResultCallAdapterFactory())
            .build()
        retrofit.create(RetrofitApi::class.java)
    }
}