package com.example.chcmovies.repo.remote

import android.util.Log
import com.example.chcmovies.data.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ResultCallAdapterFactory(
    private val isNeedDeepCheck: Boolean = false,
    private val isSelfExceptionHandling: Boolean = true
) : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Flow::class.java) {
            return null
        }
        val observableType = getParameterUpperBound(0, returnType as ParameterizedType)
        val rawObservableType = getRawType(observableType)
        require(rawObservableType == Resource::class.java) { "type must be a resource" }
        require(observableType is ParameterizedType) { "resource must be parameterized" }
        val bodyType = getParameterUpperBound(0, observableType)
        Log.d("heec.choi","call")
        return ResultCallAdapter<Any>(bodyType, isNeedDeepCheck, isSelfExceptionHandling)
    }
}