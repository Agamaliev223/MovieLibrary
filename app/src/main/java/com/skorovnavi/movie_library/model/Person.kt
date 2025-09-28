package com.skorovnavi.movie_library.model

data class Person(
    val id: Int,
    val name: String,
    val photo: String,
    val sex: String,
    val profession: String?
)

enum class Sex {
    MALE, FEMALE
}