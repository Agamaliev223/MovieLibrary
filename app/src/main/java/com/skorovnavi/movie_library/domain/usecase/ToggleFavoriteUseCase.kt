package com.skorovnavi.movie_library.domain.usecase

import com.skorovnavi.movie_library.domain.repository.FavoritesRepository
import com.skorovnavi.movie_library.model.Movie
import com.skorovnavi.movie_library.model.MovieDetails

class ToggleFavoriteUseCase(
    private val favoritesRepo: FavoritesRepository
) {
    suspend operator fun invoke(movie: Movie) {
        favoritesRepo.toggleFavorite(
            basic = movie,
            full = null
        )
    }

    suspend operator fun invoke(details: MovieDetails) {
        val basicMovie = Movie(
            id = details.id,
            name = details.name,
            year = details.year,
            rating = details.rating,
            poster = details.poster,
            genres = details.genres,
            movieLength = details.movieLength,
            inFavorites = details.inFavorites
        )

        favoritesRepo.toggleFavorite(
            basic = basicMovie,
            full = details
        )
    }
}