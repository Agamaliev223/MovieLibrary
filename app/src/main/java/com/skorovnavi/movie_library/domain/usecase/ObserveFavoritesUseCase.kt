package com.skorovnavi.movie_library.domain.usecase

import com.skorovnavi.movie_library.domain.repository.FavoritesRepository
import com.skorovnavi.movie_library.model.Movie
import kotlinx.coroutines.flow.Flow

class ObserveFavoritesUseCase(private val favoritesRepo: FavoritesRepository) {
    operator fun invoke(): Flow<List<Movie>> = favoritesRepo.observeFavorites()
}