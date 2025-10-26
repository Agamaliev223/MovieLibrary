package com.skorovnavi.movie_library.data.mappers

import com.skorovnavi.movie_library.data.local.Converters
import com.skorovnavi.movie_library.data.local.MovieEntity
import com.skorovnavi.movie_library.data.local.PosterEmbeddable
import com.skorovnavi.movie_library.data.local.RatingEmbeddable
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
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


fun MovieDto.toDomain(): Movie = Movie(
    id = id,
    name = name,
    year = year,
    rating = rating?.toDomain(),
    poster = poster?.toDomain(),
    genres = genres.mapNotNull { it.name?.let(::Genre) },
    movieLength = movieLength,
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
    persons = persons.map {
        Person(
            it.id,
            it.name,
            it.photo ?: "",
            parseSex(it.sex),
            it.profession,
        )
    },
    movieLength = movieLength,
    ageRating = ageRating,
)

fun Movie.toEntity(): MovieEntity = MovieEntity(
    id = id,
    name = name,
    year = year,
    rating = rating?.let { RatingEmbeddable(it.kp, it.imdb) },
    poster = poster?.let { PosterEmbeddable(it.url, it.previewUrl) },
    genresJson = Json.encodeToString(genres.map { mapOf("name" to it.name) }),
    movieLength = movieLength,
    inFavorites = inFavorites,
)

fun MovieDetails.toEntity(): MovieEntity {
    val genresJson = Json.encodeToString(
        genres.map { mapOf("name" to it.name) }
    )
    val countriesJson = Json.encodeToString(
        countries.map { mapOf("name" to it.name) }
    )

    val personsJson = Json.encodeToString(
        persons.map { p ->
            mapOf(
                "id" to p.id.toString(),
                "name" to p.name,
                "photo" to p.photo,
                "sex" to when (p.sex) {
                    Sex.FEMALE -> "female"
                    Sex.MALE -> "male"
                },
                "profession" to (p.profession ?: "")
            )
        }
    )

    return MovieEntity(
        id = id,
        name = name,
        year = year,
        rating = rating?.let { RatingEmbeddable(it.kp, it.imdb) },
        poster = poster?.let { PosterEmbeddable(it.url, it.previewUrl) },
        genresJson = genresJson,
        countriesJson = countriesJson,
        personsJson = personsJson,
        description = description,
        movieLength = movieLength,
        ageRating = ageRating,
        inFavorites = inFavorites,
    )
}

fun MovieEntity.toDomainMovie(): Movie = Movie(
    id = id,
    name = name,
    year = year,
    rating = rating?.let { Rating(kp = it.kp, imdb = it.imdb) },
    poster = poster?.let { Poster(url = it.url, previewUrl = it.previewUrl) },
    genres = Converters.toGenres(genresJson).mapNotNull { it.name?.let(::Genre) },
    movieLength = movieLength,
    inFavorites = true,
)

fun MovieEntity.toDomainDetails(): MovieDetails = MovieDetails(
    id = id,
    name = name,
    year = year,
    description = description,
    rating = rating?.let { Rating(kp = it.kp, imdb = it.imdb) },
    poster = poster?.let { Poster(url = it.url, previewUrl = it.previewUrl) },
    genres = Converters.toGenres(genresJson).mapNotNull { it.name?.let(::Genre) },
    countries = Converters.toCountries(countriesJson).mapNotNull { it.name?.let(::Country) },
    persons = Converters.toPersons(personsJson)
        .map {
            Person(
                id = it.id,
                name = it.name,
                photo = it.photo ?: "",
                sex = parseSex(it.sex),
                profession = it.profession,
            )
        },
    movieLength = movieLength,
    ageRating = ageRating,
    inFavorites = inFavorites,
)

fun PosterDto.toDomain(): Poster = Poster(url = url, previewUrl = previewUrl)
fun RatingDto.toDomain(): Rating = Rating(kp = kp, imdb = imdb)

fun parseSex(s: String?): Sex = when (s?.lowercase()) {
    "female" -> Sex.FEMALE
    else -> Sex.MALE
}
