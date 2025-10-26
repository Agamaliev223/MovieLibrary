package com.skorovnavi.movie_library.navigation

import com.skorovnavi.movie_library.R

sealed class BottomNavItem(
    val route: String,
    val icon: Int,
    val title: Int,
) {
    object Movies : BottomNavItem(
        route = "movies",
        icon = R.drawable.ic_movies,
        title = R.string.movies_title,
    )

    object Favorites : BottomNavItem(
        route = "favoritess",
        icon = R.drawable.ic_star,
        title = R.string.favorites_title,
    )
}