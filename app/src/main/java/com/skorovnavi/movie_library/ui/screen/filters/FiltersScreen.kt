package com.skorovnavi.movie_library.ui.screen.filters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.skorovnavi.movie_library.di.AppViewModelProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltersScreen(
    onDone: (FiltersUiState) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FiltersViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f, fill = true)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = state.year?.toString() ?: "",
                onValueChange = { viewModel.updateYear(it) },
                label = { Text("Год выпуска") },
                placeholder = { Text("например 2021") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )

            OutlinedTextField(
                value = state.genre?.name ?: "",
                onValueChange = { viewModel.updateGenre(it) },
                label = { Text("Жанр") },
                placeholder = { Text("например Блокбастер") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )

            OutlinedTextField(
                value = state.country?.name ?: "",
                onValueChange = { viewModel.updateCountry(it) },
                label = { Text("Страна") },
                placeholder = { Text("например Россия") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            OutlinedButton(
                onClick = { viewModel.clearAll() },
                modifier = Modifier.weight(1f),
            ) { Text("Сбросить") }

            Button(
                onClick = {
                    viewModel.saveFilters()
                    onDone(viewModel.uiState.value)
                },
                modifier = Modifier.weight(1f),
            ) { Text("Готово") }
        }
    }
}