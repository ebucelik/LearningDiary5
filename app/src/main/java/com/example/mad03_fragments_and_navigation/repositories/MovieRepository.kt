package com.example.mad03_fragments_and_navigation.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mad03_fragments_and_navigation.database.MovieDao
import com.example.mad03_fragments_and_navigation.models.Movie
import com.example.mad03_fragments_and_navigation.viewmodels.MovieFavoritesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MovieRepository(private val movieDao: MovieDao) {
    // TODO implement me

    val movieList = MutableLiveData<List<Movie>>()

    companion object {
        // For Singleton instantiation
        @Volatile private var instance: MovieRepository? = null

        fun getInstance(dao: MovieDao) =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(dao).also { instance = it }
            }
    }

    suspend fun create(movie: Movie){
        withContext(Dispatchers.IO){
            movieDao.create(movie)
        }
    }

    suspend fun update(movie: Movie){
        withContext(Dispatchers.IO) {
            movieDao.update(movie)
        }
    }

    suspend fun delete(movieId: Long){
        withContext(Dispatchers.IO) {
            movieDao.delete(movieId)
        }
    }

    suspend fun clearTable(){
        withContext(Dispatchers.IO){
            movieDao.clearTable()
        }
    }

    suspend fun getAll(){
        withContext(Dispatchers.IO) {
            movieList.postValue(movieDao.getAll())
        }
    }
}