package com.skorovnavi.movie_library.ui.screen.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skorovnavi.movie_library.domain.usecase.GetMoviesUseCase
import com.skorovnavi.movie_library.domain.usecase.SearchMoviesUseCase
import com.skorovnavi.movie_library.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieListUiState())
    val uiState: StateFlow<MovieListUiState> = _uiState.asStateFlow()

    fun loadNextPage() {
        if (_uiState.value.isLoadingNextPage) return

        _uiState.update { it.copy(isLoadingNextPage = true) }

        viewModelScope.launch {
            try {
                val nextPage = _uiState.value.currentPage + 1

                val nextMovies = if (_uiState.value.searchQuery != null) {
                    searchMoviesUseCase(
                        query = _uiState.value.searchQuery!!,
                        page = nextPage,
                        limit = 10
                    )
                } else {
                    getMoviesUseCase(
                        page = nextPage,
                        limit = 10,
                        filters = mapOf("notNullFields" to "poster.url")
                    )
                }

                _uiState.update { state ->
                    state.copy(
                        movies = state.movies + nextMovies,
                        currentPage = nextPage,
                        isLoadingNextPage = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoadingNextPage = false, error = e.message) }
            }
        }
    }

    fun searchMovies(query: String) {
        viewModelScope.launch {
            try {
                _uiState.update {
                    it.copy(
                        isLoading = true,
                        searchQuery = if (query.isBlank()) null else query,
                        error = null
                    )
                }

                val movies = if (query.isBlank()) {
                    getMoviesUseCase(filters = mapOf("notNullFields" to "poster.url"))
                } else {
                    searchMoviesUseCase(query = query)
                }

                _uiState.update {
                    it.copy(
                        movies = movies,
                        isLoading = false,
                        currentPage = 1
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Search error"
                    )
                }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

    init {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                val movies = getMoviesUseCase(filters = mapOf("notNullFields" to "poster.url")) // хотелось чтобы по красоте всё было, с картинками
                _uiState.update {
                    it.copy(
                        movies = movies,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Unknown error"
                    )
                }
            }
        }
    }
}

data class MovieListUiState(
    val movies: List<Movie> = emptyList(),
    val searchQuery: String? = null,
    val currentPage: Int = 1,
    val isLoading: Boolean = false,
    val isLoadingNextPage: Boolean = false,
    val error: String? = null
) {
    val showEmptyState: Boolean
        get() = movies.isEmpty() && !isLoading && error == null

    val showLoading: Boolean
        get() = isLoading && movies.isEmpty()

    val showContent: Boolean
        get() = movies.isNotEmpty() && !isLoading && error == null

    val showError: Boolean
        get() = error != null && movies.isEmpty()
}