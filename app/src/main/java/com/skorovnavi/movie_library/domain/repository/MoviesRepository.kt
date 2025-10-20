package com.skorovnavi.movie_library.domain.repository

import com.skorovnavi.movie_library.model.Movie
import com.skorovnavi.movie_library.model.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    suspend fun searchMovies(
        page: Int = 1,
        limit: Int = 20,
        filters: Map<String, String> = emptyMap()
    ): List<Movie>

    suspend fun searchMoviesByName(
        query: String,
        page: Int = 1,
        limit: Int = 20
    ): List<Movie>

    suspend fun getMovieDetails(id: Long): MovieDetails

    fun observeCachedMovies(): Flow<List<Movie>>
}