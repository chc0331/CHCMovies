package com.example.chcmovies.ui.activities.feed

import android.util.Log
import androidx.databinding.ObservableInt
import androidx.lifecycle.*
import com.example.chcmovies.R
import com.example.chcmovies.data.FeedItem
import com.example.chcmovies.data.Resource
import com.example.chcmovies.repo.remote.MovieInfo
import com.example.chcmovies.repo.remote.MoviesRepo
import com.example.chcmovies.ui.SingleLiveEvent
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FeedViewModel @Inject constructor(
    moviesRepo: MoviesRepo
) : ViewModel() {

    private var order = MutableLiveData<Int>()
    val sortIcon = ObservableInt(R.drawable.ic_star)

    val openGithub = SingleLiveEvent<Boolean>()

    companion object {
        const val SORTING_BY_RATING = 1
        const val SORTING_BY_YEAR = 2
    }

    init {
        order.value = SORTING_BY_RATING
    }

    //order 변경 시, order 값에 맞게 Movie 리스트를 Sorting해서 값을 받아옴
    val moviesList: LiveData<Resource<List<FeedItem>>> = order.switchMap { order ->
        moviesRepo
            .getTop250Movies()
            .map {
                when (it.status) {

                    Resource.Status.LOADING -> {
                        Resource.loading()
                    }

                    Resource.Status.SUCCESS -> {
                        val movies = it.data!!
                        val feedItems = convertToFeedItem(movies, order)
                        Resource.success(feedItems)
                    }

                    Resource.Status.ERROR -> {
                        Resource.error(it.message!!)
                    }
                }
            }.asLiveData(viewModelScope.coroutineContext)
    }

    private fun convertToFeedItem(movies: List<MovieInfo>, sorting: Int): List<FeedItem> {
        val genreSet = mutableSetOf<String>()
        val feedList = mutableListOf<FeedItem>()

        for (movie in movies) {
            for (genre in movie.genre) {
                genreSet.add(genre)
            }
        }

        for ((index, genre) in genreSet.withIndex()) {
            val genreMovies = movies.filter {
                it.genre.contains(genre)
            }.toMutableList()

            when (sorting) {
                SORTING_BY_RATING ->
                    genreMovies.sortByDescending { it.rating }
                SORTING_BY_YEAR ->
                    genreMovies.sortByDescending { it.year }
            }
            feedList.add(FeedItem(genre, genreMovies))
        }
        return feedList
    }

    fun onToggleHeartButtonClicked() {
        openGithub.value = true
    }

    fun onToggleOrderButtonClicked() {
        val nextIcon = if (sortIcon.get() == R.drawable.ic_calendar) {
            R.drawable.ic_star
        } else {
            R.drawable.ic_calendar
        }

        when (nextIcon) {
            R.drawable.ic_star -> {
                order.value = SORTING_BY_RATING
            }
            R.drawable.ic_calendar -> {
                order.value = SORTING_BY_YEAR
            }
        }
        sortIcon.set(nextIcon)
    }
}