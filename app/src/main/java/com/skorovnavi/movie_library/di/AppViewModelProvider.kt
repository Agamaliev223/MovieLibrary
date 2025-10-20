package com.skorovnavi.movie_library.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.skorovnavi.movie_library.MovieLibraryApplication
import com.skorovnavi.movie_library.ui.screen.detail.MovieDetailViewModel
import com.skorovnavi.movie_library.ui.screen.list.MovieListViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {

        initializer {
            MovieListViewModel(
                mockData = movieApplication().container.mockData
            )
        }

        initializer {
            MovieDetailViewModel(
                mockData = movieApplication().container.mockData
            )
        }

    }
}

fun CreationExtras.movieApplication(): MovieLibraryApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MovieLibraryApplication)