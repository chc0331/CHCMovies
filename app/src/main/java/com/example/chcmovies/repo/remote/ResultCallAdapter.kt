package com.example.chcmovies.repo.remote

import android.util.Log
import com.example.chcmovies.data.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.awaitResponse
import java.lang.reflect.Type

/*
Call<R>을 T타입으로 변환해주는 인터페이스이고, CallAdapter.Factory에 의해 인스턴스 생성

*/
class ResultCallAdapter<R : Any>(
    private val responseType: Type,
    private val isNeedDeepCheck: Boolean,
    private val isSelfExceptionHandling: Boolean
) : CallAdapter<R, Flow<Resource<R>>> {
    override fun responseType(): Type = responseType

    override fun adapt(call: Call<R>): Flow<Resource<R>> = flow {
        emit(Resource.loading())

        val resp = call.awaitResponse()

        if (resp.isSuccessful) {
            Log.d("heec.choi","success")
            emit(Resource.create(resp, isNeedDeepCheck))
        } else {
            Log.d("heec.choi","fail")
            emit(Resource.create(Throwable(resp.message())))
        }
    }.catch { error ->
        if (isSelfExceptionHandling) {
            emit(Resource.create(error))
        } else {
            throw error
        }
    }
}