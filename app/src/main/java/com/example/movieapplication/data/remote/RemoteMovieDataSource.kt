package com.example.movieapplication.data.remote

import com.example.movieapplication.application.AppConstants
import com.example.movieapplication.data.model.MovieList
import com.example.movieapplication.domain.WebService

class RemoteMovieDataSourse(private val webService: WebService) {
    suspend fun getUpcomingMovies(): MovieList = webService.getUpcomingMovies(AppConstants.API_KEY)
    suspend fun getTopRatedMovies(): MovieList = webService.getTopRatedMovies(AppConstants.API_KEY)
    suspend fun getPopularMovies(): MovieList = webService.getPopularMovies(AppConstants.API_KEY)
}