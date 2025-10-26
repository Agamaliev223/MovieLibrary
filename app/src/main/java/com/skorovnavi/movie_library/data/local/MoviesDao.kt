package com.skorovnavi.movie_library.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
    @Query("SELECT * FROM movies ORDER BY year DESC")
    fun observeFavorites(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE id = :id LIMIT 1")
    suspend fun getFavoriteById(id: Long): MovieEntity?

    @Query("SELECT * FROM movies ORDER BY year DESC")
    suspend fun getFavoritesOnce(): List<MovieEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM movies WHERE id = :id)")
    suspend fun isFavorite(id: Long): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(movie: MovieEntity)

    @Query("DELETE FROM movies WHERE id = :id")
    suspend fun removeFavorite(id: Long)
}