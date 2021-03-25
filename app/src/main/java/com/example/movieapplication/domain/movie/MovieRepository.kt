package com.example.movieapplication.domain.movie

import com.example.movieapplication.data.model.movie.MovieList

interface MovieRepository {
    suspend fun getUpcomingMovies(): MovieList
    suspend fun getTopRatedMovies(): MovieList
    suspend fun getPopularMovies(): MovieList
}