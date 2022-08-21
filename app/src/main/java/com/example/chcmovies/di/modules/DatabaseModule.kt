package com.example.chcmovies.di.modules

import android.content.Context
import androidx.room.Room
import com.example.chcmovies.repo.local.AppDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "topcorn_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideMovieDao(appDatabase: AppDatabase) = appDatabase.movieDao()
}