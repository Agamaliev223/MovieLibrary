package com.skorovnavi.movie_library.ui.screen.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skorovnavi.movie_library.domain.repository.FilterRepository
import com.skorovnavi.movie_library.domain.usecase.GetMovieDetailsUseCase
import com.skorovnavi.movie_library.domain.usecase.GetMoviesUseCase
import com.skorovnavi.movie_library.domain.usecase.ObserveFavoriteIdsUseCase
import com.skorovnavi.movie_library.domain.usecase.ToggleFavoriteUseCase
import com.skorovnavi.movie_library.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val observeFavoriteIdsUseCase: ObserveFavoriteIdsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val filterRepo: FilterRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieListUiState())
    val uiState: StateFlow<MovieListUiState> = _uiState.asStateFlow()

    init {
        observeFavoriteIds()
        observeFilters()
    }

    private fun observeFavoriteIds() {
        viewModelScope.launch {
            observeFavoriteIdsUseCase()
                .collect { favIds ->
                    _uiState.update { state ->
                        state.copy(
                            movies = state.movies.map { movie ->
                                movie.copy(inFavorites = favIds.contains(movie.id))
                            }
                        )
                    }
                }
        }
    }

    private fun observeFilters() {
        viewModelScope.launch {
            filterRepo.observeFilters().collect { filters ->
                val map = mutableMapOf<String, String>()
                filters.year?.let { map["year"] = it.toString() }
                filters.genre?.name?.let { map["genres.name"] = it }
                filters.country?.name?.let { map["countries.name"] = it }

                _uiState.update { it.copy(filters = map) }

                loadPage(page = 1, append = false)
            }
        }
    }

    private fun loadPage(page: Int, append: Boolean) {
        val snapshot = _uiState.value
        val filters = snapshot.filters
        val query = snapshot.searchQuery

        if (append) {
            _uiState.update { it.copy(isLoadingNextPage = true) }
        } else {
            _uiState.update { it.copy(isLoading = true, error = null) }
        }

        viewModelScope.launch {
            try {
                val movies = getMoviesUseCase(
                    query = query,
                    page = page,
                    limit = 10,
                    filters = filters.ifEmpty { mapOf("notNullFields" to "poster.url") }
                )

                _uiState.update { state ->
                    state.copy(
                        movies = if (append) state.movies + movies else movies,
                        currentPage = page,
                        isLoading = false,
                        isLoadingNextPage = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isLoadingNextPage = false,
                        error = e.message ?: "Loading error"
                    )
                }
            }
        }
    }

    fun loadNextPage() {
        val snapshot = _uiState.value
        if (snapshot.isLoadingNextPage) return

        val nextPage = snapshot.currentPage + 1
        loadPage(page = nextPage, append = true)
    }

    fun searchMovies(query: String) {
        _uiState.update {
            it.copy(
                searchQuery = query.ifBlank { null },
                error = null
            )
        }
        loadPage(page = 1, append = false)
    }

    fun changeFavorites(movieId: Long) {
        val movie = _uiState.value.movies.firstOrNull { it.id == movieId } ?: return

        viewModelScope.launch {
            try {
                if (movie.inFavorites) {
                    toggleFavoriteUseCase(movie)
                    return@launch
                }

                val fullDetails = try {
                    getMovieDetailsUseCase(movie.id)
                } catch (_: Exception) {
                    null
                }

                if (fullDetails != null) {
                    Log.d("MyTag", "$fullDetails")
                    toggleFavoriteUseCase(details = fullDetails)
                } else {
                    toggleFavoriteUseCase(movie = movie)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = e.message ?: "Can't update favorites"
                    )
                }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}


data class MovieListUiState(
    val movies: List<Movie> = emptyList(),
    val filters: Map<String, String> = emptyMap(),
    val searchQuery: String? = null,
    val currentPage: Int = 1,
    val isLoading: Boolean = false,
    val isLoadingNextPage: Boolean = false,
    val error: String? = null,
) {
    val showEmptyState: Boolean
        get() = movies.isEmpty() && !isLoading && error == null

    val showLoading: Boolean
        get() = isLoading && movies.isEmpty()

    val showContent: Boolean
        get() = movies.isNotEmpty() && !isLoading && error == null

    val showError: Boolean
        get() = error != null && movies.isEmpty()

    val haveFilters: Boolean
        get() = filters.isNotEmpty()
}