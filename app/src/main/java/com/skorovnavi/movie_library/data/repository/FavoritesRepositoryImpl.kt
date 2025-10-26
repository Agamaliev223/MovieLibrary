package com.skorovnavi.movie_library.data.repository

import com.skorovnavi.movie_library.data.local.MoviesDao
import com.skorovnavi.movie_library.data.mappers.toDomainDetails
import com.skorovnavi.movie_library.data.mappers.toDomainMovie
import com.skorovnavi.movie_library.data.mappers.toEntity
import com.skorovnavi.movie_library.domain.repository.FavoritesRepository
import com.skorovnavi.movie_library.model.Movie
import com.skorovnavi.movie_library.model.MovieDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoritesRepositoryImpl(
    private val dao: MoviesDao
) : FavoritesRepository {
    override suspend fun toggleFavorite(
        basic: Movie,
        full: MovieDetails?
    ) {
        val alreadyFavorite = dao.isFavorite(basic.id)

        if (alreadyFavorite) {
            dao.removeFavorite(basic.id)
            return
        }

        val entityToSave = full?.copy(inFavorites = true)
            ?.toEntity() ?: basic.copy(inFavorites = true).toEntity()

        dao.addFavorite(entityToSave)
    }

    override fun observeFavorites(): Flow<List<Movie>> = dao.observeFavorites()
        .map { list ->
            list.map { entity ->
                entity.toDomainMovie()
            }
        }

    override fun observeFavoriteIds(): Flow<Set<Long>> = dao.observeFavorites()
        .map { list -> list.map { it.id }.toSet() }

    override suspend fun getFavoritesOnce(): List<Movie> =
        dao.getFavoritesOnce().map { it.toDomainMovie() }

    override suspend fun getFavoriteDetails(id: Long): MovieDetails? =
        dao.getFavoriteById(id)?.toDomainDetails()

    override suspend fun isFavorite(id: Long): Boolean = dao.isFavorite(id)
}