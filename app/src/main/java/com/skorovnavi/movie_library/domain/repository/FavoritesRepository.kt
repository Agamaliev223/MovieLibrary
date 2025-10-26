package com.skorovnavi.movie_library.domain.repository

import com.skorovnavi.movie_library.model.Movie
import com.skorovnavi.movie_library.model.MovieDetails
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    suspend fun toggleFavorite(
        basic: Movie,
        full: MovieDetails? = null,
    )

    fun observeFavorites(): Flow<List<Movie>>
    fun observeFavoriteIds(): Flow<Set<Long>>

    suspend fun getFavoritesOnce(): List<Movie>
    suspend fun getFavoriteDetails(id: Long): MovieDetails?
    suspend fun isFavorite(id: Long): Boolean
}