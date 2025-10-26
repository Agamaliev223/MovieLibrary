package com.skorovnavi.movie_library.data.local

import androidx.room.TypeConverter
import com.skorovnavi.movie_library.data.remote.CountryDto
import com.skorovnavi.movie_library.data.remote.GenreDto
import com.skorovnavi.movie_library.data.remote.PersonDto
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object Converters {
    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    @JvmStatic
    fun toGenres(value: String): List<GenreDto> = runCatching {
        json.decodeFromString<List<GenreDto>>(value)
    }.getOrDefault(emptyList())

    @TypeConverter
    @JvmStatic
    fun toCountries(value: String): List<CountryDto> = runCatching {
        json.decodeFromString<List<CountryDto>>(value)
    }.getOrDefault(emptyList())

    @TypeConverter
    @JvmStatic
    fun toPersons(value: String): List<PersonDto> = runCatching {
        json.decodeFromString<List<PersonDto>>(value)
    }.getOrDefault(emptyList())
}