package com.skorovnavi.movie_library.ui.screen.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.skorovnavi.movie_library.di.AppViewModelProvider
import com.skorovnavi.movie_library.ui.screen.list.components.MovieCard

@Composable
fun MovieListScreen(
    onMovieClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MovieListViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    var searchQuery by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(uiState.error) {
        if (uiState.error != null) {
            viewModel.clearError()
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Искать фильм...") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            keyboardActions = KeyboardActions(onSearch = {
                viewModel.searchMovies(searchQuery)
            }),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            trailingIcon = {
                if (searchQuery.isNotBlank()) {
                    IconButton(onClick = {
                        searchQuery = ""
                        viewModel.searchMovies("")
                    }) {
                        Icon(Icons.Default.Close, contentDescription = "Очистить")
                    }
                }
            }
        )

        Box(modifier = Modifier.fillMaxSize()) {
            when {
                uiState.showLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                uiState.showError -> {
                    Text(
                        text = "Ошибка: ${uiState.error}",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                uiState.showEmptyState -> {
                    Text(
                        text = if (uiState.searchQuery != null)
                            "Фильмы по запросу '${uiState.searchQuery}' не найдены"
                        else "Фильмы не найдены",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                uiState.showContent -> {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(uiState.movies.size) { index ->
                            val movie = uiState.movies[index]
                            MovieCard(
                                movie = movie,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                                    .clickable { onMovieClick(movie.id) }
                            )
                        }

                        item {
                            if (uiState.isLoadingNextPage) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .align(Alignment.Center)
                                )
                            }
                        }
                    }

                    val shouldLoadNext by remember {
                        derivedStateOf {
                            val layoutInfo = listState.layoutInfo
                            val totalItems = layoutInfo.totalItemsCount
                            val lastVisible = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
                            lastVisible >= totalItems - 5 && !uiState.isLoadingNextPage
                        }
                    }

                    LaunchedEffect(shouldLoadNext) {
                        if (shouldLoadNext) {
                            viewModel.loadNextPage()
                        }
                    }
                }
            }
        }
    }
}