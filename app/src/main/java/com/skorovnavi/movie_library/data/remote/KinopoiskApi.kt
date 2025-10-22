package com.skorovnavi.movie_library.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface KinopoiskApi {
    @GET("v1.4/movie")
    suspend fun searchMovies(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20,
        @QueryMap(encoded = true) filters: Map<String, String> = emptyMap()
    ): MovieListResponseDto

    @GET("v1.4/movie/{id}")
    suspend fun getMovieDetails(@Path("id") id: Long): MovieDetailsDto

    @GET("v1.4/movie/search")
    suspend fun searchByName(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): MovieListResponseDto
}
