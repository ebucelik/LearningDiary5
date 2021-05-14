package com.example.mad03_fragments_and_navigation.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mad03_fragments_and_navigation.models.Movie

@Dao
interface MovieDao {
    // TODO implement me

    @Insert
    suspend fun create(movie: Movie): Long

    @Update
    suspend fun update(movie: Movie)

    @Query("delete from movie where movie.id = :movieId")
    suspend fun delete(movieId: Long)

    @Query("delete from movie")
    suspend fun clearTable()

    @Query("SELECT * FROM movie")
    fun getAll(): List<Movie>
}