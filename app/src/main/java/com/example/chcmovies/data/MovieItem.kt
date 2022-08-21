package com.example.chcmovies.data

import com.example.chcmovies.repo.remote.MovieInfo
import java.io.Serializable

data class MovieItem(
    val movieName: String,
    val rating: Float,
    val director: List<String>,
    val actors: List<String>,
    val genres: List<String>,
    val description: String,
    val imageUrl: String,
    val imdbUrl: String
) : Serializable {

    companion object {
        fun getMovieItem(info: MovieInfo): MovieItem {
            return MovieItem(
                info.name,
                info.rating,
                info.directors,
                info.actors,
                info.genre,
                info.desc,
                info.imageUrl,
                info.imdbUrl
            )
        }
    }
}
