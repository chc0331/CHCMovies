package com.example.chcmovies.repo.remote

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.chcmovies.data.Resource
import com.example.chcmovies.repo.local.MoviesDao
import com.example.chcmovies.utils.NetworkBoundResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.time.ExperimentalTime
import kotlin.time.hours

class MoviesRepo @Inject constructor(
    private val sharedPref: SharedPreferences,
    private val apiInterface: RetrofitApi,
    private val moviesDao: MoviesDao
) {

    companion object {
        @OptIn(ExperimentalTime::class)
        private val MOVIE_EXPIRY_IN_MILLIS = 1.hours.inMilliseconds.toLong()
        private const val KEY_LAST_SYNCED = "last_synced"
    }

    fun getTop250Movies(): Flow<Resource<List<MovieInfo>>> {

        return object : NetworkBoundResource<List<MovieInfo>, List<MovieInfo>>() {

            override fun fetchFromLocal(): Flow<List<MovieInfo>> {
                return moviesDao.getAllMovies()
            }

            override fun fetchFromRemote(): Flow<Resource<List<MovieInfo>>> {
                return apiInterface.getMovies()
            }

            override fun saveRemoteData(data: List<MovieInfo>) {
                moviesDao.deleteAll()
                moviesDao.addAll(data)
                sharedPref.edit {
                    putLong(KEY_LAST_SYNCED, System.currentTimeMillis())
                }
            }

            override fun shouldFetchFromRemote(data: List<MovieInfo>): Boolean {
                val lastSynced = sharedPref.getLong(KEY_LAST_SYNCED, -1)
                return lastSynced == -1L ||
                        data.isNullOrEmpty() ||
                        isExpired(lastSynced)
            }

        }.asFlow().flowOn(Dispatchers.IO)
    }

    private fun isExpired(lastSynced: Long): Boolean {
        val currentTime = System.currentTimeMillis()
        return (currentTime - lastSynced) >= MOVIE_EXPIRY_IN_MILLIS
    }


}