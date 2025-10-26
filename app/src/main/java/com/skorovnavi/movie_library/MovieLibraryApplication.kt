package com.skorovnavi.movie_library

import android.app.Application
import com.skorovnavi.movie_library.di.AppContainer
import com.skorovnavi.movie_library.di.AppDataContainer

class MovieLibraryApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}