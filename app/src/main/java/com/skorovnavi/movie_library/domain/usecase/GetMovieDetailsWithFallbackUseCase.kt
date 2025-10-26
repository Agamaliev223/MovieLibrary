package com.skorovnavi.movie_library.domain.usecase

import com.skorovnavi.movie_library.domain.repository.FavoritesRepository
import com.skorovnavi.movie_library.model.MovieDetails

class GetMovieDetailsWithFallbackUseCase(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val favoritesRepo: FavoritesRepository,
) {
    suspend operator fun invoke(id: Long): MovieDetails {
        val localDetails: MovieDetails? = favoritesRepo.getFavoriteDetails(id)

        return try {
            val remoteDetails = getMovieDetailsUseCase(id)
            remoteDetails
        } catch (e: Exception) {
            localDetails ?: throw e
        }
    }
}