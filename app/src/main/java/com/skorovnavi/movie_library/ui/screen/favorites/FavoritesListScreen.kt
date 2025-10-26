package com.skorovnavi.movie_library.ui.screen.favorites

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.skorovnavi.movie_library.di.AppViewModelProvider
import com.skorovnavi.movie_library.ui.common.MovieListContent

@Composable
fun FavoritesListScreen(
    onMovieClick: (Long) -> Unit,
    onFiltersClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(state.error) {
        if (state.error != null) {
            viewModel.clearError()
        }
    }

    MovieListContent(
        state = state,
        onSearchChange = { query -> viewModel.searchInFavorites(query) },
        onFiltersClick = onFiltersClick,
        onRetryPageLoad = { },
        onLoadNextPage = { },
        onMovieClick = { id -> onMovieClick(id) },
        onToggleFavorite = { id -> viewModel.onToggleFavorite(id) },
        modifier = modifier,
    )
}