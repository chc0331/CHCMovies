package com.example.chcmovies.repo.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.chcmovies.repo.remote.MovieInfo

@Database(entities = [MovieInfo::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MoviesDao
}