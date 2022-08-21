package com.example.chcmovies.data

import com.example.chcmovies.repo.remote.MovieInfo

data class FeedItem(
    val genre: String,
    val movies: List<MovieInfo>
)
