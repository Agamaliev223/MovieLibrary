package com.skorovnavi.movie_library.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.skorovnavi.movie_library.ui.screen.detail.MovieDetailScreen
import com.skorovnavi.movie_library.ui.screen.list.MovieListScreen

@Composable
fun MovieAppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.MOVIE_LIST
    ) {
        composable(Routes.MOVIE_LIST) {
            MovieListScreen(
                onMovieClick = { movieId ->
                    navController.navigate(Routes.createMovieDetailRoute(movieId))
                },
                modifier = modifier
            )
        }

        composable(
            route = Routes.MOVIE_DETAIL,
            arguments = listOf(
                navArgument("movieId") {
                    type = NavType.LongType
                    defaultValue = 0
                }
            )
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getLong("movieId") ?: 0L
            MovieDetailScreen(
                movieId = movieId,
                modifier = modifier
            )
        }
    }
}