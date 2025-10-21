package com.skorovnavi.movie_library.ui.screen.list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.skorovnavi.movie_library.model.Movie

@Composable
fun MovieCard(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AsyncImage(
                model = movie.poster?.url,
                contentDescription = "Постер фильма ${movie.name}",
                modifier = Modifier
                    .size(100.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Crop
            )
            Column {
                Text(
                    text = movie.name ?: "Без названия",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Год: ${movie.year ?: "Неизвестно"}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Жанры: ${movie.genres.joinToString { it.name }}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Рейтинг: ${movie.rating?.kp ?: "N/A"}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}