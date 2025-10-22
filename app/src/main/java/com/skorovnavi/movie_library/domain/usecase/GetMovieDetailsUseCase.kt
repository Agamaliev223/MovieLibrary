package com.skorovnavi.movie_library.domain.usecase

import com.skorovnavi.movie_library.domain.repository.MoviesRepository

class GetMovieDetailsUseCase(private val repo: MoviesRepository) {
    suspend operator fun invoke(id: Long) = repo.getMovieDetails(id)
}
