package com.example.chcmovies.repo.remote

import com.example.chcmovies.data.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface RetrofitApi {
    @GET("top250_min.json")
    fun getMovies(): Flow<Resource<List<MovieInfo>>>
}