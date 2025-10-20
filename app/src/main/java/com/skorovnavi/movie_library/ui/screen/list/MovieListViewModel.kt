package com.skorovnavi.movie_library.ui.screen.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skorovnavi.movie_library.data.repository.MockData
import com.skorovnavi.movie_library.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val mockData: MockData
) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieListUiState>(MovieListUiState.Loading)
    val uiState: StateFlow<MovieListUiState> = _uiState.asStateFlow()

    init {
        loadMovies()
    }

    private fun loadMovies() {
        // для загрузки потом из сети
        viewModelScope.launch {
            try {
                val movies = mockData.movies
                _uiState.value = MovieListUiState.Success(movies)
            } catch (e: Exception) {
                _uiState.value = MovieListUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

sealed class MovieListUiState {
    data class Success(val movies: List<Movie>) : MovieListUiState()
    object Loading : MovieListUiState()
    data class Error(val message: String) : MovieListUiState()
}