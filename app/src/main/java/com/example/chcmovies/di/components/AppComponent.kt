package com.example.chcmovies.di.components

import android.content.Context
import com.example.chcmovies.App
import com.example.chcmovies.di.modules.AppModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/*
*  @Inject : Dagger2 can also use an instance of this object to fulfill dependencies.
*            This was done to avoid the definition of lots of @Provides methods for these objects.
* */
@Singleton
@Component(
    modules = [AppModule::class]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}