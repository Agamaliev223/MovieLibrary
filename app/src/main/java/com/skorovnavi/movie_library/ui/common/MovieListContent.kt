package com.skorovnavi.movie_library.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.skorovnavi.movie_library.ui.screen.list.MovieListUiState
import com.skorovnavi.movie_library.ui.screen.list.components.MovieCard

@Composable
fun MovieListContent(
    state: MovieListUiState,
    onSearchChange: (String) -> Unit,
    onFiltersClick: () -> Unit,
    onRetryPageLoad: () -> Unit,
    onLoadNextPage: () -> Unit,
    onMovieClick: (Long) -> Unit,
    onToggleFavorite: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    var searchQuery by remember { mutableStateOf(state.searchQuery ?: "") }
    val listState = rememberLazyListState()

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Искать фильм...") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .weight(0.8f),
                keyboardActions = KeyboardActions(onSearch = {
                    onSearchChange(searchQuery)
                }),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                trailingIcon = {
                    if (searchQuery.isNotBlank()) {
                        IconButton(onClick = {
                            searchQuery = ""
                            onSearchChange(searchQuery)
                        }) {
                            Icon(Icons.Default.Close, contentDescription = "Очистить")
                        }
                    }
                }
            )

            Box(
                contentAlignment = Alignment.TopEnd,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .weight(0.2f),
            ) {
                IconButton(onClick = onFiltersClick) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Фильтры",
                        modifier = Modifier.size(40.dp),
                    )
                }

                if (state.haveFilters) {
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .clip(CircleShape)
                            .background(Color.Yellow)
                    )
                }
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            when {
                state.showLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                state.showError -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Ошибка: ${state.error}",
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        IconButton(onClick = onRetryPageLoad) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "Повторить"
                            )
                        }
                    }
                }

                state.showEmptyState -> {
                    Text(
                        text = if (state.searchQuery != null)
                            "Фильмы по запросу '${state.searchQuery}' не найдены"
                        else "Здесь пока пусто",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                state.showContent -> {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(state.movies.size) { index ->
                            val movie = state.movies[index]

                            MovieCard(
                                movie = movie,
                                onChangeFavorites = { onToggleFavorite(movie.id) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                                    .clickable { onMovieClick(movie.id) }
                            )
                        }

                        item {
                            if (state.isLoadingNextPage) {
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

                            lastVisible >= totalItems - 5 && !state.isLoadingNextPage
                        }
                    }

                    LaunchedEffect(shouldLoadNext) {
                        if (shouldLoadNext) {
                            onLoadNextPage()
                        }
                    }
                }
            }
        }
    }
}
