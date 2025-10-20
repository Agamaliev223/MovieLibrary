package com.skorovnavi.movie_library.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.skorovnavi.movie_library.MovieLibraryApplication
import com.skorovnavi.movie_library.domain.usecase.GetMovieDetailsUseCase
import com.skorovnavi.movie_library.domain.usecase.GetMoviesUseCase
import com.skorovnavi.movie_library.ui.screen.detail.MovieDetailViewModel
import com.skorovnavi.movie_library.ui.screen.list.MovieListViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {

        initializer {
            MovieListViewModel(
                getMoviesUseCase = GetMoviesUseCase(
                    repo = movieApplication().container.moviesRepository
                )
            )
        }

        initializer {
            MovieDetailViewModel(
                getMovieDetailsUseCase = GetMovieDetailsUseCase(
                    repo = movieApplication().container.moviesRepository
                )
            )
        }
    }
}

fun CreationExtras.movieApplication(): MovieLibraryApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MovieLibraryApplication)