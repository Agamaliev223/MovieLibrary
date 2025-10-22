package com.skorovnavi.movie_library.di

import com.skorovnavi.movie_library.data.remote.KinopoiskApi
import com.skorovnavi.movie_library.data.remote.NetworkModule.retrofit
import com.skorovnavi.movie_library.data.repository.MockData
import com.skorovnavi.movie_library.data.repository.MoviesRepositoryImpl
import com.skorovnavi.movie_library.domain.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers

interface AppContainer {
    val mockData: MockData
    val moviesRepository: MoviesRepository
}

class AppDataContainer : AppContainer {

    override val mockData: MockData = MockData()

    override val moviesRepository: MoviesRepository = MoviesRepositoryImpl(
        api = retrofit.create(KinopoiskApi::class.java),
        dispatcher = Dispatchers.IO
    )

}