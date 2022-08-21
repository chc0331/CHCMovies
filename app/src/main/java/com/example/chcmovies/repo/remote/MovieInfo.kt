package com.example.chcmovies.repo.remote

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable


@Entity(
    tableName = "movies"
)
data class MovieInfo(
    @SerializedName("actors")
    val actors: List<String>,
    @SerializedName("desc")
    val desc: String,
    @SerializedName("directors")
    val directors: List<String>,
    @SerializedName("genre")
    val genre: List<String>,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("thumb_url")
    val thumbUrl: String,
    @SerializedName("imdb_url")
    val imdbUrl: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("rating")
    val rating: Float,
    @SerializedName("year")
    val year: Int
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}