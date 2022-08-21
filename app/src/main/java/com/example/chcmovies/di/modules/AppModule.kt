package com.example.chcmovies.di.modules

import dagger.Module
import dagger.android.AndroidInjectionModule


@Module(
    includes = [AndroidInjectionModule::class,
        ActivitiesBuilderModule::class,
        ViewModelModule::class,
        PreferenceModule::class,
        NetworkModule::class,
        DatabaseModule::class]
)
class AppModule {
}