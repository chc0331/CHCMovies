package com.example.chcmovies.di.modules

import com.example.chcmovies.ui.activities.feed.FeedActivity
import com.example.chcmovies.ui.activities.movie.MovieActivity
import com.example.chcmovies.ui.activities.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesBuilderModule {

    @ContributesAndroidInjector
    abstract fun getSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    abstract fun getFeedActivity(): FeedActivity

    @ContributesAndroidInjector
    abstract fun getMovieActivity(): MovieActivity
}