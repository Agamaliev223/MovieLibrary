package com.skorovnavi.movie_library.domain.repository

import com.skorovnavi.movie_library.ui.screen.filters.FiltersUiState
import kotlinx.coroutines.flow.Flow

interface FilterRepository {
    fun observeFilters(): Flow<FiltersUiState>
    suspend fun saveFilters(filters: FiltersUiState)
    suspend fun clear()
}