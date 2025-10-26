package com.skorovnavi.movie_library.data.repository

import com.skorovnavi.movie_library.data.mappers.toDomain
import com.skorovnavi.movie_library.data.remote.KinopoiskApi
import com.skorovnavi.movie_library.domain.repository.MoviesRepository
import com.skorovnavi.movie_library.model.Movie
import com.skorovnavi.movie_library.model.MovieDetails
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class MoviesRepositoryImpl(
    private val api: KinopoiskApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : MoviesRepository {

    private val _cached = MutableStateFlow<List<Movie>>(emptyList())
    private val cached = _cached.asStateFlow()
    private val moviesCache = mutableMapOf<String, List<Movie>>()

    override suspend fun searchMovies(
        page: Int,
        limit: Int,
        filters: Map<String, String>
    ): List<Movie> =
        withContext(dispatcher) {
            val cacheKey = "search_${filters.hashCode()}_${page}_$limit"

            moviesCache[cacheKey]?.let { cachedMovies ->
                _cached.value = if (page <= 1) cachedMovies else _cached.value + cachedMovies
                return@withContext cachedMovies
            }

            val resp = api.searchMovies(page = page, limit = limit, filters = filters)
            val movies = resp.docs.map { it.toDomain() }

            moviesCache[cacheKey] = movies

            if (page <= 1) {
                _cached.value = movies
            } else {
                _cached.value += movies
            }
            movies
        }

    override suspend fun searchMoviesByName(
        query: String,
        page: Int,
        limit: Int,
        filters: Map<String, String>
    ): List<Movie> = withContext(dispatcher) {
        val cacheKey = "search_${query}_${page}_${limit}_${filters.hashCode()}"

        moviesCache[cacheKey]?.let { cachedMovies ->
            _cached.value = if (page <= 1) cachedMovies else _cached.value + cachedMovies
            return@withContext cachedMovies
        }

        val resp = api.searchByName(
            query = query,
            page = page,
            limit = limit,
            filters = filters
        )
        val movies = resp.docs.map { it.toDomain() }

        moviesCache[cacheKey] = movies

        if (page <= 1) {
            _cached.value = movies
        } else {
            _cached.value += movies
        }
        movies
    }

    override suspend fun getMovieDetails(id: Long): MovieDetails = withContext(dispatcher) {
        val dto = api.getMovieDetails(id)
        dto.toDomain()
    }

    override fun observeCachedMovies() = cached
}