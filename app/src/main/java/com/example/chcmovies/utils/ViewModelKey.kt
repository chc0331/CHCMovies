package com.example.chcmovies.utils

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

//Class를 Key로 사용할 수 있습니다.
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)