package com.skorovnavi.movie_library.data.repository

import com.skorovnavi.movie_library.model.Country
import com.skorovnavi.movie_library.model.Movie
import com.skorovnavi.movie_library.model.Rating
import com.skorovnavi.movie_library.model.Poster
import com.skorovnavi.movie_library.model.Genre
import com.skorovnavi.movie_library.model.MovieDetails
import com.skorovnavi.movie_library.model.Person
import com.skorovnavi.movie_library.model.Sex

class MockData {
    val movies = listOf(
        Movie(
            id = 535341,
            name = "1+1",
            year = 2011,
            rating = Rating(kp = 8.8, imdb = 9.1),
            poster = Poster(
                url = "https://avatars.mds.yandex.net/get-ott/13074011/2a0000019504097dd3ec1bf7f8db56b4026e/375x375",
                previewUrl = "https://avatars.mds.yandex.net/get-ott/13074011/2a0000019504097dd3ec1bf7f8db56b4026e/375x375"
            ),
            genres = listOf(Genre("драма"), Genre("комедия"), Genre("биография")),
            movieLength = 112,
            inFavorites = false,
        ),
        Movie(
            id = 1143242,
            name = "Джентльмены",
            year = 2019,
            rating = Rating(kp = 8.5, imdb = 8.2),
            poster = Poster(
                url = "https://avatars.mds.yandex.net/get-ott/1648503/2a000001711b57abb795e9276957168f83e9/orig",
                previewUrl = "https://avatars.mds.yandex.net/get-ott/1648503/2a000001711b57abb795e9276957168f83e9/orig"
            ),
            genres = listOf(Genre("криминал"), Genre("комедия"), Genre("боевик")),
            movieLength = 113,
            inFavorites = false,
        ),
        Movie(
            id = 462682,
            name = "Волк с Уолл-стрит",
            year = 2013,
            rating = Rating(kp = 8.0, imdb = 9.2),
            poster = Poster(
                url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQOLIyHjOuuloBpwARPNDoH7CYOwX26G-0ctg&s",
                previewUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQOLIyHjOuuloBpwARPNDoH7CYOwX26G-0ctg&s"
            ),
            genres = listOf(Genre("криминал"), Genre("комедия"), Genre("биография")),
            movieLength = 180,
            inFavorites = false,
        ),
        Movie(
            id = 447301,
            name = "Начало",
            year = 2010,
            rating = Rating(kp = 8.7, imdb = 8.4),
            poster = Poster(
                url = "https://avatars.mds.yandex.net/get-kinopoisk-image/1629390/8ab9a119-dd74-44f0-baec-0629797483d7/600x900",
                previewUrl = "https://avatars.mds.yandex.net/get-kinopoisk-image/1629390/8ab9a119-dd74-44f0-baec-0629797483d7/600x900"
            ),
            genres = listOf(Genre("фантастика"), Genre("боевик"), Genre("триллер")),
            movieLength = 148,
            inFavorites = false,
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
                    url = "https://avatars.mds.yandex.net/get-ott/13074011/2a0000019504097dd3ec1bf7f8db56b4026e/375x375",
                    previewUrl = "https://avatars.mds.yandex.net/get-ott/13074011/2a0000019504097dd3ec1bf7f8db56b4026e/375x375"
                ),
                genres = listOf(Genre("драма"), Genre("комедия"), Genre("биография")),
                countries = listOf(Country("Франция")),
                persons = listOf(
                    Person(
                        6317,
                        "Оливье Накаш",
                        "https://st.kp.yandex.net/images/actor_iphone/iphone360_6317.jpg",
                        Sex.MALE,
                        "режиссер"
                    ),
                    Person(
                        6318,
                        "Эрик Толедано",
                        "https://st.kp.yandex.net/images/actor_iphone/iphone360_6318.jpg",
                        Sex.MALE,
                        "режиссер"
                    ),
                    Person(
                        589,
                        "Франсуа Клюзе",
                        "https://st.kp.yandex.net/images/actor_iphone/iphone360_589.jpg",
                        Sex.MALE,
                        "актер"
                    ),
                    Person(
                        19289,
                        "Омар Си",
                        "https://st.kp.yandex.net/images/actor_iphone/iphone360_19289.jpg",
                        Sex.MALE,
                        "актер"
                    )
                ),
                movieLength = 112,
                ageRating = 16,
                inFavorites = false,
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
                ageRating = 16,
                inFavorites = false,
            )
        }
    }
}