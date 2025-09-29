package com.skorovnavi.movie_library.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.skorovnavi.movie_library.R
import com.skorovnavi.movie_library.ui.screen.detail.MovieDetailScreen
import com.skorovnavi.movie_library.ui.screen.list.MovieListScreen

@Composable
fun MovieAppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            MovieBottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Movies.route,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Movies.route) {
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
                        defaultValue = 0L
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
}
