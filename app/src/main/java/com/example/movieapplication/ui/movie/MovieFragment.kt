package com.example.movieapplication.ui.movie

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.example.movieapplication.R
import com.example.movieapplication.core.Resource
import com.example.movieapplication.data.local.AppDatabase
import com.example.movieapplication.data.local.LocalMovieDataSource

import com.example.movieapplication.data.model.Movie
import com.example.movieapplication.data.remote.RemoteMovieDataSourse
import com.example.movieapplication.databinding.FragmentMovieBinding
import com.example.movieapplication.domain.MovieRepositoryImpl
import com.example.movieapplication.domain.RetrofitClient
import com.example.movieapplication.presentation.MovieViewModel
import com.example.movieapplication.presentation.MovieViewModelFactory
import com.example.movieapplication.ui.movie.adapters.MovieAdapter
import com.example.movieapplication.ui.movie.adapters.concat.PopularConcatAdapter
import com.example.movieapplication.ui.movie.adapters.concat.TopRatedConcatAdapter
import com.example.movieapplication.ui.movie.adapters.concat.UpcomingConcatAdapter

class MovieFragment : Fragment(R.layout.fragment_movie), MovieAdapter.OnMovieClickListener {
    private lateinit var binding: FragmentMovieBinding
    private val vieModel by viewModels<MovieViewModel> {
        MovieViewModelFactory(
            MovieRepositoryImpl(
                RemoteMovieDataSourse(RetrofitClient.webservice),
                LocalMovieDataSource(AppDatabase.getDatabase(requireContext()).movieDao())
            )
        ) //aca se diferencia el implement no la interfaz
    }
    private lateinit var concatAdapter: ConcatAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)//busca en paralelo
        concatAdapter = ConcatAdapter()
        vieModel.fetchMainScreenMovies().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    //Log.d("LiveData", "Upcoming: ${result.data.first} \n \n Popular: ${result.data.second} \n" +
                    //      " \n TopRated: ${result.data.third} ")
                    binding.progressBar.visibility = View.GONE
                    concatAdapter.apply {
                        addAdapter(
                            0,
                            UpcomingConcatAdapter(
                                MovieAdapter(
                                    result.data.first.results,
                                    this@MovieFragment
                                )
                            )
                        )
                        addAdapter(
                            1,
                            TopRatedConcatAdapter(
                                MovieAdapter(
                                    result.data.second.results,
                                    this@MovieFragment
                                )
                            )
                        )
                        addAdapter(
                            2,
                            PopularConcatAdapter(
                                MovieAdapter(
                                    result.data.third.results,
                                    this@MovieFragment
                                )
                            )
                        )
                    }
                    binding.rvMovies.adapter = concatAdapter
                }
                is Resource.Failure -> {
                    Log.d("Error", "${result.exception}")
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
    }

     override fun onMovieClick(movie: Movie) {
        val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(
            movie.poster_path,
            movie.backdrop_path,
            movie.vote_average.toFloat(),
            movie.vote_count,
            movie.overview,
            movie.title,
            movie.original_language,
            movie.release_date
        )
        findNavController().navigate(action)
    }
}