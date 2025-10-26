package com.skorovnavi.movie_library.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Long,
    val name: String? = null,
    val year: Int? = null,

    @Embedded(prefix = "rating_")
    val rating: RatingEmbeddable? = null,

    @Embedded(prefix = "poster_")
    val poster: PosterEmbeddable? = null,

    val genresJson: String = "[]",
    val countriesJson: String = "[]",
    val personsJson: String = "[]",

    val description: String? = null,
    val movieLength: Int? = null,
    val ageRating: Int? = null,
    val inFavorites: Boolean = true,
)

data class RatingEmbeddable(
    val kp: Double? = null,
    val imdb: Double? = null,
)

data class PosterEmbeddable(
    val url: String? = null,
    val previewUrl: String? = null,
)