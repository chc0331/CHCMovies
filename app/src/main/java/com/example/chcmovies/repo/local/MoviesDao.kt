package com.example.chcmovies.repo.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.chcmovies.repo.remote.MovieInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flow<List<MovieInfo>>

    @Query("DELETE FROM movies")
    fun deleteAll()

    @Insert
    fun addAll(data: List<MovieInfo>)
}