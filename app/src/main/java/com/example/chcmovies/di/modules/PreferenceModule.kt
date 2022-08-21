package com.example.chcmovies.di.modules

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PreferenceModule {

    @Singleton
    @Provides
    fun provideSharedPreference(context: Context): SharedPreferences {
        return androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
    }
}