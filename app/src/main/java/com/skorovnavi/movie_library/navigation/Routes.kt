package com.skorovnavi.movie_library.navigation

object Routes {
    const val MOVIE_LIST = "movie_list"
    const val MOVIE_FAVORITES_LIST = "movie_favorites_list"

    const val MOVIE_DETAIL = "movie_detail/{movieId}"
    const val MOVIE_FILTERS = "movie_filters"

    fun createMovieDetailRoute(movieId: Long) = "movie_detail/$movieId"
}