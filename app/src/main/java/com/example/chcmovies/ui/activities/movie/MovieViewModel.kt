package com.example.chcmovies.ui.activities.movie

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.chcmovies.ui.SingleLiveEvent
import javax.inject.Inject

class MovieViewModel @Inject constructor() : ViewModel() {

    val openImdb = SingleLiveEvent<Boolean>()

    fun imdbClicked() {
        openImdb.value = true
    }
}