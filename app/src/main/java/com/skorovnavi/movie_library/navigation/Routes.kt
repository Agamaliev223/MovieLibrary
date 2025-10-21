package com.skorovnavi.movie_library.navigation

object Routes {
    const val MOVIE_LIST = "movie_list"
    const val MOVIE_DETAIL = "movie_detail/{movieId}"

    fun createMovieDetailRoute(movieId: Long) = "movie_detail/$movieId"
}