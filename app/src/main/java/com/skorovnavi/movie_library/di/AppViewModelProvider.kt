package com.skorovnavi.movie_library.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.skorovnavi.movie_library.MovieLibraryApplication
import com.skorovnavi.movie_library.domain.usecase.GetMovieDetailsUseCase
import com.skorovnavi.movie_library.domain.usecase.GetMovieDetailsWithFallbackUseCase
import com.skorovnavi.movie_library.domain.usecase.GetMoviesUseCase
import com.skorovnavi.movie_library.domain.usecase.ObserveFavoriteIdsUseCase
import com.skorovnavi.movie_library.domain.usecase.ObserveFavoritesUseCase
import com.skorovnavi.movie_library.domain.usecase.ToggleFavoriteUseCase
import com.skorovnavi.movie_library.ui.screen.detail.MovieDetailViewModel
import com.skorovnavi.movie_library.ui.screen.favorites.FavoritesViewModel
import com.skorovnavi.movie_library.ui.screen.filters.FiltersViewModel
import com.skorovnavi.movie_library.ui.screen.list.MovieListViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {

        initializer {
            MovieListViewModel(
                getMoviesUseCase = GetMoviesUseCase(
                    moviesRepo = movieApplication().container.moviesRepository,
                    favoritesRepo = movieApplication().container.favoritesRepository,
                ),
                observeFavoriteIdsUseCase = ObserveFavoriteIdsUseCase(
                    favoritesRepo = movieApplication().container.favoritesRepository,
                ),
                getMovieDetailsUseCase = GetMovieDetailsUseCase(
                    moviesRepo = movieApplication().container.moviesRepository,
                    favoritesRepo = movieApplication().container.favoritesRepository,
                ),
                toggleFavoriteUseCase = ToggleFavoriteUseCase(
                    favoritesRepo = movieApplication().container.favoritesRepository,
                ),
                filterRepo = movieApplication().container.filterRepository,
            )
        }

        initializer {
            FavoritesViewModel(
                observeFavoritesUseCase = ObserveFavoritesUseCase(
                    favoritesRepo = movieApplication().container.favoritesRepository,
                ),
                toggleFavoriteUseCase = ToggleFavoriteUseCase(
                    favoritesRepo = movieApplication().container.favoritesRepository,
                ),
                filterRepo = movieApplication().container.filterRepository,
            )
        }

        initializer {
            MovieDetailViewModel(
                getMovieDetailsWithFallbackUseCase = GetMovieDetailsWithFallbackUseCase(
                    getMovieDetailsUseCase = GetMovieDetailsUseCase(
                        moviesRepo = movieApplication().container.moviesRepository,
                        favoritesRepo = movieApplication().container.favoritesRepository,
                    ),
                    favoritesRepo = movieApplication().container.favoritesRepository,
                ),
            )
        }

        initializer {
            FiltersViewModel(filterRepo = movieApplication().container.filterRepository)
        }
    }
}

fun CreationExtras.movieApplication(): MovieLibraryApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MovieLibraryApplication)