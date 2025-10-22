package com.skorovnavi.movie_library.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skorovnavi.movie_library.data.repository.MockData
import com.skorovnavi.movie_library.domain.usecase.GetMovieDetailsUseCase
import com.skorovnavi.movie_library.model.MovieDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieDetailUiState>(MovieDetailUiState.Loading)
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()

    fun loadMovie(movieId: Long) {
        viewModelScope.launch {
            try {
                val movie = getMovieDetailsUseCase(movieId)
                _uiState.value = MovieDetailUiState.Success(movie)
            } catch (e: Exception) {
                _uiState.value = MovieDetailUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

sealed class MovieDetailUiState {
    data class Success(val movie: MovieDetails) : MovieDetailUiState()
    object Loading : MovieDetailUiState()
    data class Error(val message: String) : MovieDetailUiState()
}