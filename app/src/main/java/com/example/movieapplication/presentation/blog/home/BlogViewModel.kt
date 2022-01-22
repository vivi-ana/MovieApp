package com.example.movieapplication.presentation.blog.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.movieapplication.core.Resource
import com.example.movieapplication.domain.blog.home.BlogRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class BlogViewModel (private val repo: BlogRepository): ViewModel() {
    fun fetchLatestPost() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repo.getLatestPosts())
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    class BlogViewModelFactory(private val repo: BlogRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(BlogRepository::class.java).newInstance(repo)
        }
    }
}