package com.skorovnavi.movie_library.domain.usecase

import com.skorovnavi.movie_library.domain.repository.MoviesRepository

class SearchMoviesUseCase(private val repo: MoviesRepository) {
    suspend operator fun invoke(query: String, page: Int = 1, limit: Int = 20) =
        repo.searchMoviesByName(query, page, limit)
}