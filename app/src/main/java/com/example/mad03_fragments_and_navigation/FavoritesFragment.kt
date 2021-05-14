package com.example.mad03_fragments_and_navigation

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mad03_fragments_and_navigation.adapters.FavoritesListAdapter
import com.example.mad03_fragments_and_navigation.database.AppDatabase
import com.example.mad03_fragments_and_navigation.databinding.FragmentFavoritesBinding
import com.example.mad03_fragments_and_navigation.models.Movie
import com.example.mad03_fragments_and_navigation.repositories.MovieRepository
import com.example.mad03_fragments_and_navigation.viewmodels.MovieFavoritesViewModel
import com.example.mad03_fragments_and_navigation.viewmodels.MovieFavoritesViewModelFactory


class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var movieFavoritesViewModel: MovieFavoritesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = AppDatabase.getInstance(application)!!.movieDao

        val repo = MovieRepository.getInstance(dataSource)

        val factory = MovieFavoritesViewModelFactory(repo)

        movieFavoritesViewModel = ViewModelProvider(this, factory).get(MovieFavoritesViewModel::class.java)

        binding.lifecycleOwner = this

        binding.movieViewModel = movieFavoritesViewModel

        movieFavoritesViewModel.movieList.observe(viewLifecycleOwner, Observer { movieList ->
            val adapter = movieFavoritesViewModel.movieList.value?.let {
                FavoritesListAdapter(
                    dataSet = it,     // start with empty list
                    onDeleteClicked = {movieId -> onDeleteMovieClicked(movieId)},   // pass functions to adapter
                    onEditClicked = {movie -> onEditMovieClicked(movie)},           // pass functions to adapter
                )
            }

            adapter?.updateDataSet(movieList)

            with(binding){
                recyclerView.adapter = adapter
            }
        })

        return binding.root
    }

    // This is called when recyclerview item edit button is clicked
    private fun onEditMovieClicked(movieObj: Movie){
        // TODO implement me
        val builder: AlertDialog.Builder = android.app.AlertDialog.Builder(this.context)
        builder.setTitle("Add a Note")

        // Set up the input
        val input = EditText(this.context)
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setHint("Enter Note")
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        // Set up the buttons
        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
            // Here you get get input text from the Edittext
            var m_Text = input.text.toString()

            movieObj.note = m_Text

            movieFavoritesViewModel.updateMovie(movieObj)
        })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }

    // This is called when recyclerview item remove button is clicked
    private fun onDeleteMovieClicked(movieId: Long){
        // TODO implement me
        movieFavoritesViewModel.deleteMovie(movieId)
    }
}