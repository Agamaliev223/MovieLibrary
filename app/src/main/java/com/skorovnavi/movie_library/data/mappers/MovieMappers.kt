package com.skorovnavi.movie_library.data.mappers

import com.skorovnavi.movie_library.data.remote.MovieDetailsDto
import com.skorovnavi.movie_library.data.remote.MovieDto
import com.skorovnavi.movie_library.data.remote.PosterDto
import com.skorovnavi.movie_library.data.remote.RatingDto
import com.skorovnavi.movie_library.model.Country
import com.skorovnavi.movie_library.model.Genre
import com.skorovnavi.movie_library.model.Movie
import com.skorovnavi.movie_library.model.MovieDetails
import com.skorovnavi.movie_library.model.Person
import com.skorovnavi.movie_library.model.Poster
import com.skorovnavi.movie_library.model.Rating
import com.skorovnavi.movie_library.model.Sex


fun MovieDto.toDomain(): Movie = Movie(
    id = id,
    name = name,
    year = year,
    rating = rating?.toDomain(),
    poster = poster?.toDomain(),
    genres = genres.mapNotNull { it.name?.let(::Genre) },
    movieLength = movieLength
)

fun MovieDetailsDto.toDomain(): MovieDetails = MovieDetails(
    id = id,
    name = name,
    year = year,
    description = description,
    rating = rating?.toDomain(),
    poster = poster?.toDomain(),
    genres = genres.mapNotNull { it.name?.let(::Genre) },
    countries = countries.mapNotNull { it.name?.let(::Country) },
    persons = persons.map { Person(it.id, it.name, it.photo ?: "", parseSex(it.sex), it.profession) },
    movieLength = movieLength,
    ageRating = ageRating
)

fun PosterDto.toDomain(): Poster = Poster(url = url, previewUrl = previewUrl)
fun RatingDto.toDomain(): Rating = Rating(kp = kp, imdb = imdb)

fun parseSex(s: String?): Sex = when (s?.lowercase()) {
    "female" -> Sex.FEMALE
    else -> Sex.MALE
}
