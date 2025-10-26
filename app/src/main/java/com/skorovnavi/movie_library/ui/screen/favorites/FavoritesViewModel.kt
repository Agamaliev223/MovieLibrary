package com.skorovnavi.movie_library.ui.screen.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skorovnavi.movie_library.domain.repository.FilterRepository
import com.skorovnavi.movie_library.domain.usecase.ObserveFavoritesUseCase
import com.skorovnavi.movie_library.domain.usecase.ToggleFavoriteUseCase
import com.skorovnavi.movie_library.model.Movie
import com.skorovnavi.movie_library.ui.screen.list.MovieListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val observeFavoritesUseCase: ObserveFavoritesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val filterRepo: FilterRepository,
) : ViewModel() {
    private var allFavorites: List<Movie> = emptyList()

    private val _uiState = MutableStateFlow(
        MovieListUiState(
            movies = emptyList(),
            filters = emptyMap(),
            searchQuery = null,
            currentPage = 1,
            isLoading = true,
            isLoadingNextPage = false,
            error = null,
        )
    )
    val uiState: StateFlow<MovieListUiState> = _uiState.asStateFlow()

    init {
        observeFavorites()
        observeFilters()
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            observeFavoritesUseCase()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = e.message ?: "Не удалось загрузить избранное"
                        )
                    }
                }
                .collect { listFromDb ->
                    allFavorites = listFromDb

                    _uiState.update { state ->
                        state.copy(
                            movies = applyAllFilters(
                                source = allFavorites,
                                query = state.searchQuery,
                                uiFilters = state.filters
                            ),
                            isLoading = false,
                            error = null
                        )
                    }
                }
        }
    }

    private fun observeFilters() {
        viewModelScope.launch {
            filterRepo.observeFilters().collect { filtersPref ->
                val newFiltersMap = buildMap {
                    filtersPref.year?.let { put("year", it.toString()) }
                    filtersPref.genre?.name?.let { put("genres.name", it) }
                    filtersPref.country?.name?.let { put("countries.name", it) }
                }

                _uiState.update { old ->
                    old.copy(
                        filters = newFiltersMap,
                        movies = applyAllFilters(
                            source = allFavorites,
                            query = old.searchQuery,
                            uiFilters = newFiltersMap
                        )
                    )
                }
            }
        }
    }

    fun searchInFavorites(query: String) {
        val normalizedQuery = query.ifBlank { null }

        _uiState.update { state ->
            val newMovies = applyAllFilters(
                source = allFavorites,
                query = normalizedQuery,
                uiFilters = state.filters
            )

            state.copy(
                searchQuery = normalizedQuery,
                movies = newMovies,
            )
        }
    }

    fun onToggleFavorite(movieId: Long) {
        val movie = _uiState.value.movies.firstOrNull { it.id == movieId } ?: return

        viewModelScope.launch {
            try {
                toggleFavoriteUseCase(movie)
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = e.message ?: "Не удалось изменить избранное"
                    )
                }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

    private fun applyAllFilters(
        source: List<Movie>,
        query: String?,
        uiFilters: Map<String, String>,
    ): List<Movie> {
        val afterUiFilters = applyUiFilters(source, uiFilters)
        return applySearchFilter(afterUiFilters, query)
    }

    private fun applySearchFilter(
        source: List<Movie>,
        query: String?
    ): List<Movie> {
        if (query.isNullOrBlank()) return source

        val q = query.trim().lowercase()
        return source.filter { movie ->
            movie.name?.lowercase()?.contains(q) == true
        }
    }

    private fun applyUiFilters(
        source: List<Movie>,
        uiFilters: Map<String, String>,
    ): List<Movie> {
        var result = source
        uiFilters["year"]?.let { requiredYear ->
            result = result.filter { movie ->
                movie.year?.toString() == requiredYear
            }
        }

        uiFilters["genres.name"]?.let { requiredGenre ->
            val match = requiredGenre.lowercase()
            result = result.filter { movie ->
                movie.genres.any { g ->
                    g.name.lowercase() == match
                }
            }
        }

//         uiFilters["countries.name"]?.let { requiredCountry ->
//             // Movie (не MovieDetails) не хранит страну, так что пока пропустил этот фильтр
//         }

        return result
    }
}
