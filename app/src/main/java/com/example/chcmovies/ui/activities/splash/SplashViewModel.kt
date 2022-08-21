package com.example.chcmovies.ui.activities.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.chcmovies.ui.activities.feed.FeedActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class SplashViewModel @Inject constructor() : ViewModel() {

    companion object {
        val TAG = SplashViewModel::class.java.simpleName
        const val SPLASH_DURATION = 1000L
    }

    val versionName = "1.0"

    val launchActivityEvent = flowOf(FeedActivity::class.simpleName)
        .onStart { delay(SPLASH_DURATION) }
        .flowOn(Dispatchers.Main)
        .asLiveData()
}