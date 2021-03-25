package com.example.movieapplication.data.remote.movie

import com.example.movieapplication.utils.AppConstants
import com.example.movieapplication.data.model.movie.MovieList
import com.example.movieapplication.domain.movie.WebService

class RemoteMovieDataSourse(private val webService: WebService) {
    suspend fun getUpcomingMovies(): MovieList = webService.getUpcomingMovies(AppConstants.API_KEY)
    suspend fun getTopRatedMovies(): MovieList = webService.getTopRatedMovies(AppConstants.API_KEY)
    suspend fun getPopularMovies(): MovieList = webService.getPopularMovies(AppConstants.API_KEY)
}