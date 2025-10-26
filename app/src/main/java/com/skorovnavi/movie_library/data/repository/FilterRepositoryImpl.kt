package com.skorovnavi.movie_library.data.repository

import com.skorovnavi.movie_library.data.cache.FilterPreferencesDataStore
import com.skorovnavi.movie_library.domain.repository.FilterRepository
import com.skorovnavi.movie_library.ui.screen.filters.FiltersUiState
import kotlinx.coroutines.flow.Flow

class FilterRepositoryImpl(private val dataStore: FilterPreferencesDataStore) : FilterRepository {
    override fun observeFilters(): Flow<FiltersUiState> = dataStore.observeFilters()
    override suspend fun saveFilters(filters: FiltersUiState) = dataStore.saveFilters(filters)
    override suspend fun clear() = dataStore.clear()
}