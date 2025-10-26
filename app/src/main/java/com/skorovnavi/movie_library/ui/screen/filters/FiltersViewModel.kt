package com.skorovnavi.movie_library.ui.screen.filters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skorovnavi.movie_library.data.remote.CountryDto
import com.skorovnavi.movie_library.data.remote.GenreDto
import com.skorovnavi.movie_library.domain.repository.FilterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FiltersViewModel(private val filterRepo: FilterRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(FiltersUiState())
    val uiState: StateFlow<FiltersUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            filterRepo.observeFilters().collect { prefs ->
                _uiState.value = prefs
            }
        }
    }

    fun updateYear(year: String) {
        _uiState.update { it.copy(year = year.toIntOrNull()) }
    }

    fun updateGenre(genre: String) {
        _uiState.update { it.copy(genre = if (genre.isBlank()) null else GenreDto(name = genre)) }
    }

    fun updateCountry(country: String) {
        _uiState.update { it.copy(country = if (country.isBlank()) null else CountryDto(name = country)) }
    }

    fun clearAll() {
        _uiState.update { FiltersUiState() }
        viewModelScope.launch { filterRepo.clear() }
    }

    fun saveFilters() {
        viewModelScope.launch { filterRepo.saveFilters(_uiState.value) }
    }
}

data class FiltersUiState(
    val year: Int? = null,
    val genre: GenreDto? = null,
    val country: CountryDto? = null,
)