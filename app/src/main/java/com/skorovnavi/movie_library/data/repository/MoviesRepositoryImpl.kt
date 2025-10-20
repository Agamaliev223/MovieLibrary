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

    override suspend fun searchMovies(
        page: Int,
        limit: Int,
        filters: Map<String, String>
    ): List<Movie> = withContext(dispatcher) {
        val resp = api.searchMovies(page = page, limit = limit, filters = filters)
        val movies = resp.docs.map { it.toDomain() }
        _cached.value = movies
        movies
    }

    override suspend fun getMovieDetails(id: Long): MovieDetails = withContext(dispatcher) {
        val dto = api.getMovieDetails(id)
        dto.toDomain()
    }

    override fun observeCachedMovies() = cached
}

