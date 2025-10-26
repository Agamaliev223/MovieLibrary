package com.skorovnavi.movie_library.ui.screen.list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.skorovnavi.movie_library.model.Movie

@Composable
fun MovieCard(
    movie: Movie,
    onChangeFavorites: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
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

            Column(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .weight(1f),
            ) {
                Text(
                    text = movie.name ?: "Без названия",
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
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

            IconButton(
                onClick = onChangeFavorites,
                modifier = Modifier.wrapContentWidth(),
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "добавить в избранное",
                    tint = if (movie.inFavorites) Color.Yellow else Color.Black,
                )
            }
        }
    }
}