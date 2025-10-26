package com.skorovnavi.movie_library.model

// https://api.kinopoisk.dev/documentation#/
data class Movie(
    val id: Long,
    val name: String?,
    val year: Int?,
    val rating: Rating?,
    val poster: Poster?,
    val genres: List<Genre>,
    val movieLength: Int?,
    val inFavorites: Boolean = false,
)

data class MovieDetails(
    val id: Long,
    val name: String?,
    val year: Int?,
    val description: String?,
    val rating: Rating?,
    val poster: Poster?,
    val genres: List<Genre>,
    val countries: List<Country>,
    val persons: List<Person>,
    val movieLength: Int?,
    val ageRating: Int?,
    val inFavorites: Boolean = false,
)