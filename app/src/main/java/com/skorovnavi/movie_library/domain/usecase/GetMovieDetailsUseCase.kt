package com.skorovnavi.movie_library.domain.usecase

import com.skorovnavi.movie_library.domain.repository.FavoritesRepository
import com.skorovnavi.movie_library.domain.repository.MoviesRepository
import com.skorovnavi.movie_library.model.MovieDetails

class GetMovieDetailsUseCase(
    private val moviesRepo: MoviesRepository,
    private val favoritesRepo: FavoritesRepository,
) {
    suspend operator fun invoke(id: Long): MovieDetails {
        val details = moviesRepo.getMovieDetails(id)
        val isFav = favoritesRepo.isFavorite(id)

        return details.copy(inFavorites = isFav)
    }
}
