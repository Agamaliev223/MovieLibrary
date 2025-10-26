package com.skorovnavi.movie_library.di

import android.content.Context
import androidx.room.Room
import com.skorovnavi.movie_library.data.cache.FilterPreferencesDataStore
import com.skorovnavi.movie_library.data.local.MoviesDao
import com.skorovnavi.movie_library.data.local.MoviesDatabase
import com.skorovnavi.movie_library.data.remote.KinopoiskApi
import com.skorovnavi.movie_library.data.remote.NetworkModule.retrofit
import com.skorovnavi.movie_library.data.repository.FavoritesRepositoryImpl
import com.skorovnavi.movie_library.data.repository.FilterRepositoryImpl
import com.skorovnavi.movie_library.data.repository.MockData
import com.skorovnavi.movie_library.data.repository.MoviesRepositoryImpl
import com.skorovnavi.movie_library.domain.repository.FavoritesRepository
import com.skorovnavi.movie_library.domain.repository.FilterRepository
import com.skorovnavi.movie_library.domain.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers

interface AppContainer {
    val mockData: MockData
    val moviesRepository: MoviesRepository
    val favoritesRepository: FavoritesRepository
    val filterRepository: FilterRepository
    val database: MoviesDatabase
    val moviesDao: MoviesDao
}

class AppDataContainer(context: Context) : AppContainer {

    override val mockData: MockData = MockData()

    override val moviesRepository: MoviesRepository = MoviesRepositoryImpl(
        api = retrofit.create(KinopoiskApi::class.java),
        dispatcher = Dispatchers.IO
    )

    override val filterRepository: FilterRepository = FilterRepositoryImpl(
        dataStore = FilterPreferencesDataStore(context = context)
    )

    override val database: MoviesDatabase by lazy {
        Room.databaseBuilder(
            context.applicationContext,
            MoviesDatabase::class.java,
            "movies_cache"
        ).build()
    }

    override val moviesDao: MoviesDao by lazy {
        database.moviesDao()
    }

    override val favoritesRepository: FavoritesRepository = FavoritesRepositoryImpl(dao = moviesDao)
}