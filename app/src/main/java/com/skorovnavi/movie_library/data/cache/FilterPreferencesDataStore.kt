package com.skorovnavi.movie_library.data.cache

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.skorovnavi.movie_library.ui.screen.filters.FiltersUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val DATASTORE_NAME = "filter_prefs"

val Context.filterDataStore by preferencesDataStore(name = DATASTORE_NAME)

object FilterKeys {
    val YEAR = intPreferencesKey("year")
    val GENRE = stringPreferencesKey("genre")
    val COUNTRY = stringPreferencesKey("country")
}

class FilterPreferencesDataStore(private val context: Context) {
    fun observeFilters(): Flow<FiltersUiState> {
        return context.filterDataStore.data.map { prefs ->
            FiltersUiState(
                year = prefs[FilterKeys.YEAR],
                genre = prefs[FilterKeys.GENRE]?.let { name ->
                    com.skorovnavi.movie_library.data.remote.GenreDto(name)
                },
                country = prefs[FilterKeys.COUNTRY]?.let { name ->
                    com.skorovnavi.movie_library.data.remote.CountryDto(name)
                }
            )
        }
    }

    suspend fun saveFilters(filters: FiltersUiState) {
        context.filterDataStore.edit { prefs ->
            if (filters.year != null) prefs[FilterKeys.YEAR] = filters.year else prefs.remove(
                FilterKeys.YEAR
            )
            if (filters.genre != null) prefs[FilterKeys.GENRE] = filters.genre.name ?: ""
            else prefs.remove(FilterKeys.GENRE)
            if (filters.country != null) prefs[FilterKeys.COUNTRY] = filters.country.name ?: ""
            else prefs.remove(FilterKeys.COUNTRY)
        }
    }

    suspend fun clear() {
        context.filterDataStore.edit { it.clear() }
    }
}