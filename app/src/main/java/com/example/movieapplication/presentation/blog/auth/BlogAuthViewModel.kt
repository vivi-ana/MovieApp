package com.example.movieapplication.presentation.blog.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.movieapplication.core.Resource
import com.example.movieapplication.domain.blog.auth.AuthRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class BlogAuthViewModel(private val repo:AuthRepository): ViewModel() {
    fun signIn(email:String, password:String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repo.signIn(email, password)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
    fun signUp(email: String,password: String,username:String)= liveData(Dispatchers.IO){
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repo.signUp(email, password, username)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
    class BlogAuthViewModelFactory(private val repo:AuthRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return BlogAuthViewModel(repo) as T
        }
    }
}