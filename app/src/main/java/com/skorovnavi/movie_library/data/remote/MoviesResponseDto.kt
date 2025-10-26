package com.skorovnavi.movie_library.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieListResponseDto(
    val docs: List<MovieDto> = emptyList(),
    val total: Int? = null,
)

@Serializable
data class MovieDto(
    val id: Long,
    val name: String? = null,
    val year: Int? = null,
    val rating: RatingDto? = null,
    val poster: PosterDto? = null,
    val genres: List<GenreDto> = emptyList(),
    @SerialName("movieLength")
    val movieLength: Int? = null,
)

@Serializable
data class MovieDetailsDto(
    val id: Long,
    val name: String? = null,
    val year: Int? = null,
    val description: String? = null,
    val rating: RatingDto? = null,
    val poster: PosterDto? = null,
    val genres: List<GenreDto> = emptyList(),
    val countries: List<CountryDto> = emptyList(),
    val persons: List<PersonDto> = emptyList(),
    @SerialName("movieLength")
    val movieLength: Int? = null,
    @SerialName("ageRating")
    val ageRating: Int? = null,
)

@Serializable
data class GenreDto(val name: String? = null)

@Serializable
data class CountryDto(val name: String? = null)

@Serializable
data class PersonDto(
    val id: Int,
    val name: String,
    val photo: String? = null,
    val sex: String? = null,
    val profession: String? = null
)

@Serializable
data class PosterDto(val url: String? = null, val previewUrl: String? = null)

@Serializable
data class RatingDto(
    @SerialName("kp") val kp: Double? = null,
    @SerialName("imdb") val imdb: Double? = null
)
