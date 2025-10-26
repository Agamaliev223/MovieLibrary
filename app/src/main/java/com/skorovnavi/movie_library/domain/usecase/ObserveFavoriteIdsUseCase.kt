package com.skorovnavi.movie_library.domain.usecase

import com.skorovnavi.movie_library.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow

class ObserveFavoriteIdsUseCase(private val favoritesRepo: FavoritesRepository) {
    operator fun invoke(): Flow<Set<Long>> = favoritesRepo.observeFavoriteIds()
}