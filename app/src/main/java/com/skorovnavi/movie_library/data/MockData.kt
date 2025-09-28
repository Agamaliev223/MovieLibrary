package com.skorovnavi.movie_library.data

import com.skorovnavi.movie_library.model.Country
import com.skorovnavi.movie_library.model.Movie
import com.skorovnavi.movie_library.model.Rating
import com.skorovnavi.movie_library.model.Poster
import com.skorovnavi.movie_library.model.Genre
import com.skorovnavi.movie_library.model.MovieDetails
import com.skorovnavi.movie_library.model.Person
import com.skorovnavi.movie_library.model.Sex

object MockData {
    val sampleMovies = listOf(
        Movie(
            id = 535341,
            name = "1+1",
            year = 2011,
            rating = Rating(kp = 8.8, imdb = 9.1),
            poster = Poster(
                url = "https://avatars.mds.yandex.net/get-kinopoisk-image/1946459/7c4c4a74-7e4c-4e84-b6d6-38a3e4a89a58/orig",
                previewUrl = "https://avatars.mds.yandex.net/get-kinopoisk-image/1946459/7c4c4a74-7e4c-4e84-b6d6-38a3e4a89a58/x1000"
            ),
            genres = listOf(Genre("драма"), Genre("комедия"), Genre("биография")),
            movieLength = 112
        ),
        Movie(
            id = 1143242,
            name = "Джентльмены",
            year = 2019,
            rating = Rating(kp = 8.5, imdb = 8.2),
            poster = Poster(
                url = "https://avatars.mds.yandex.net/get-kinopoisk-image/4303601/1c7870a2-4b9c-4b5c-9b9a-5c4b1c5b5b5b/orig",
                previewUrl = "https://avatars.mds.yandex.net/get-kinopoisk-image/4303601/1c7870a2-4b9c-4b5c-9b9a-5c4b1c5b5b5b/x1000"
            ),
            genres = listOf(Genre("криминал"), Genre("комедия"), Genre("боевик")),
            movieLength = 113
        ),
        Movie(
            id = 462682,
            name = "Волк с Уолл-стрит",
            year = 2013,
            rating = Rating(kp = 8.0, imdb = 9.2),
            poster = Poster(
                url = "https://avatars.mds.yandex.net/get-kinopoisk-image/1773646/0c5b7e68-6a5c-4b5c-9b9a-5c4b1c5b5b5b/orig",
                previewUrl = "https://avatars.mds.yandex.net/get-kinopoisk-image/1773646/0c5b7e68-6a5c-4b5c-9b9a-5c4b1c5b5b5b/x1000"
            ),
            genres = listOf(Genre("криминал"), Genre("комедия"), Genre("биография")),
            movieLength = 180
        ),
        Movie(
            id = 447301,
            name = "Начало",
            year = 2010,
            rating = Rating(kp = 8.7, imdb = 8.4),
            poster = Poster(
                url = "https://avatars.mds.yandex.net/get-kinopoisk-image/1773646/0c5b7e68-6a5c-4b5c-9b9a-5c4b1c5b5b5b/orig",
                previewUrl = "https://avatars.mds.yandex.net/get-kinopoisk-image/1773646/0c5b7e68-6a5c-4b5c-9b9a-5c4b1c5b5b5b/x1000"
            ),
            genres = listOf(Genre("фантастика"), Genre("боевик"), Genre("триллер")),
            movieLength = 148
        )
    )

    fun getMovieDetails(movieId: Long): MovieDetails {
        return when (movieId) {
            535341L -> MovieDetails(
                id = 535341,
                name = "1+1",
                year = 2011,
                description = "Пострадав в результате несчастного случая, богатый аристократ Филипп нанимает в помощники человека, который менее всего подходит для этой работы, — молодого жителя предместья Дрисса, только что освободившегося из тюрьмы.",
                rating = Rating(kp = 8.8, imdb = 7.6),
                poster = Poster(
                    url = "https://avatars.mds.yandex.net/get-kinopoisk-image/1946459/7c4c4a74-7e4c-4e84-b6d6-38a3e4a89a58/orig",
                    previewUrl = "https://avatars.mds.yandex.net/get-kinopoisk-image/1946459/7c4c4a74-7e4c-4e84-b6d6-38a3e4a89a58/x1000"
                ),
                genres = listOf(Genre("драма"), Genre("комедия"), Genre("биография")),
                countries = listOf(Country("Франция")),
                persons = listOf(
                    Person(6317, "Оливье Накаш", "https://st.kp.yandex.net/images/actor_iphone/iphone360_6317.jpg", Sex.MALE.name, "режиссер"),
                    Person(6318, "Эрик Толедано", "https://st.kp.yandex.net/images/actor_iphone/iphone360_6318.jpg", Sex.MALE.name, "режиссер"),
                    Person(589, "Франсуа Клюзе", "https://st.kp.yandex.net/images/actor_iphone/iphone360_589.jpg", Sex.MALE.name,"актер"),
                    Person(19289, "Омар Си", "https://st.kp.yandex.net/images/actor_iphone/iphone360_19289.jpg", Sex.MALE.name,"актер")
                ),
                movieLength = 112,
                ageRating = 16
            )
            else -> MovieDetails(
                id = movieId,
                name = "Пример фильма",
                year = 2023,
                description = "Это пример описания фильма для разработки.",
                rating = Rating(kp = 7.5, imdb = 6.7),
                poster = Poster(
                    url = "https://example.com/poster.jpg",
                    previewUrl = "https://example.com/preview.jpg"
                ),
                genres = listOf(Genre("драма"), Genre("комедия")),
                countries = listOf(Country("Россия")),
                persons = listOf(),
                movieLength = 120,
                ageRating = 16
            )
        }
    }
}