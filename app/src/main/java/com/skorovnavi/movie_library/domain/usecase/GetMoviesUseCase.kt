package com.skorovnavi.movie_library.domain.usecase

import com.skorovnavi.movie_library.domain.repository.MoviesRepository

class GetMoviesUseCase(private val repo: MoviesRepository) {
    suspend operator fun invoke(page: Int = 1, limit: Int = 20, filters: Map<String,String> = emptyMap()) =
        repo.searchMovies(page, limit, filters)
}
