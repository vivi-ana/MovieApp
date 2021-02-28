package com.example.movieapplication.data.local

import com.example.movieapplication.data.model.MovieEntity
import com.example.movieapplication.data.model.MovieList
import com.example.movieapplication.data.model.toMovieList

class LocalMovieDataSource(private val movieDao: MovieDao) {
    suspend fun getUpcomingMovies(): MovieList {
        return movieDao.getAllMovies().filter { it.movie_type == "upcoming" }.toMovieList()
    }
    suspend fun getTopRatedMovies(): MovieList {
        return movieDao.getAllMovies().filter { it.movie_type == "toprated" }.toMovieList()
    }
    suspend fun getPopularMovies(): MovieList {
        return movieDao.getAllMovies().filter { it.movie_type == "popular" }.toMovieList()
    }
    suspend fun saveMovie(movie: MovieEntity){
        movieDao.saveMovie(movie)
    }
}