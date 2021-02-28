package com.example.movieapplication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.movieapplication.core.Resource
import com.example.movieapplication.domain.MovieRepository
import kotlinx.coroutines.Dispatchers

class MovieViewModel(private val repo: MovieRepository): ViewModel() {
    fun fetchMainScreenMovies() = liveData(Dispatchers.IO){
        emit(Resource.Loading())
        try { //llamada al servidor
            //emit(Resource.Success(repo.getUpcomingMovies()))
            emit(Resource.Success(Triple(repo.getUpcomingMovies(), repo.getPopularMovies(), repo.getTopRatedMovies())))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
}
class MovieViewModelFactory(private val repo: MovieRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MovieRepository::class.java).newInstance(repo)
    }
}