package com.example.chcmovies.utils

import android.util.Log
import com.example.chcmovies.data.Resource
import kotlinx.coroutines.flow.*

/**
 * A super cool utility class to provide controlled data cache-ing
 */
abstract class NetworkBoundResource<DB, REMOTE> {

    companion object {
        const val TAG = "NetworkBoundResource"
    }

    abstract fun fetchFromLocal(): Flow<DB>

    abstract fun fetchFromRemote(): Flow<Resource<REMOTE>>

    abstract fun saveRemoteData(data: REMOTE)

    abstract fun shouldFetchFromRemote(data: DB): Boolean

    fun asFlow() = flow<Resource<DB>> {

        val localData = fetchFromLocal().first()

        if (shouldFetchFromRemote(localData)) {
            Log.d(TAG, "Fetching from remote")

            fetchFromRemote()
                .collect { response ->
                    Log.d(TAG, "Fetching response status ${response.status}")
                    when (response.status) {
                        Resource.Status.LOADING -> {
                            emit(Resource.loading())
                        }

                        Resource.Status.SUCCESS -> {
                            val data = response.data!!
                            saveRemoteData(data)

                            emitLocalDbData()
                        }

                        Resource.Status.ERROR -> {
                            emit(Resource.error(response.message!!))
                        }
                    }
                }
        } else {
            Log.d(TAG, "Fetching from local")
            emitLocalDbData()
        }
    }

    private suspend fun FlowCollector<Resource<DB>>.emitLocalDbData() {
        emit(Resource.loading())

        emitAll(fetchFromLocal().map { dbData ->
            Resource.success(dbData)
        })
    }


}