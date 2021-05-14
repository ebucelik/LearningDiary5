package com.example.mad03_fragments_and_navigation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mad03_fragments_and_navigation.database.AppDatabase
import com.example.mad03_fragments_and_navigation.database.MovieDao
import com.example.mad03_fragments_and_navigation.models.Movie
import com.example.mad03_fragments_and_navigation.repositories.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieFavoritesViewModel (
    private val repository: MovieRepository
): ViewModel() {
    // TODO implement me

    val movie = MutableLiveData<Movie>()
    val movieList = MutableLiveData<List<Movie>>()

    init {
        getAllMovies()
    }

    fun getAllMovies(){
        viewModelScope.launch {
            repository.getAll()
            movieList.value = repository.movieList.value
        }
    }

    fun createMovie(newMovie: Movie){
        viewModelScope.launch {
            repository.create(newMovie)
            getAllMovies()
        }
    }

    fun updateMovie(newMovie: Movie){
        viewModelScope.launch {
            repository.update(newMovie)
            getAllMovies()
        }
    }

    fun deleteMovie(movieId: Long){
        viewModelScope.launch {
            repository.delete(movieId)
            getAllMovies()
        }
    }

    fun clearMovieList(){
        viewModelScope.launch {
            repository.clearTable()
            getAllMovies()
        }
    }
}