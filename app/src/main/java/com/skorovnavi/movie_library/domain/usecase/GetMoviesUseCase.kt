package com.skorovnavi.movie_library.domain.usecase

import com.skorovnavi.movie_library.domain.repository.FavoritesRepository
import com.skorovnavi.movie_library.domain.repository.MoviesRepository
import com.skorovnavi.movie_library.model.Movie

class GetMoviesUseCase(
    private val moviesRepo: MoviesRepository,
    private val favoritesRepo: FavoritesRepository,
) {
    suspend operator fun invoke(
        query: String? = null,
        page: Int = 1,
        limit: Int = 20,
        filters: Map<String, String> = emptyMap()
    ): List<Movie> {

        val remoteMovies = if (query.isNullOrBlank()) {
            moviesRepo.searchMovies(page, limit, filters)
        } else {
            moviesRepo.searchMoviesByName(query, page, limit, filters)
        }

        val favoriteIds = favoritesRepo
            .getFavoritesOnce()
            .map { it.id }
            .toSet()

        return remoteMovies.map { movie ->
            movie.copy(inFavorites = favoriteIds.contains(movie.id))
        }
    }
}
