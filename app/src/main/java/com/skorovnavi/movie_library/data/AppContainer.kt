package com.skorovnavi.movie_library.data

interface AppContainer {
    val mockData: MockData
}

class AppDataContainer : AppContainer {

    override val mockData: MockData = MockData()

}