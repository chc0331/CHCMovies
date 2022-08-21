package com.example.chcmovies

import android.app.Application
import com.example.chcmovies.di.components.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector() = androidInjector

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .context(this)
            .build().inject(this)
    }
}