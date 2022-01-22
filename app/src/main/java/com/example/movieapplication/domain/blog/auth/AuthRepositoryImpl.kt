package com.example.movieapplication.domain.blog.auth

import android.graphics.Bitmap
import com.example.movieapplication.data.remote.blog.auth.AuthBlogDataSource
import com.google.firebase.auth.FirebaseUser

class AuthRepositoryImpl(private val dataSource: AuthBlogDataSource):AuthRepository {
    override suspend fun signIn(email: String, password: String): FirebaseUser? = dataSource.signIn(email, password)
    override suspend fun signUp(email: String, password: String, username: String): FirebaseUser? = dataSource.signUp(email, password, username)
    override suspend fun updateProfile(imageBitmap: Bitmap, username: String) = dataSource.updateUserProfile(imageBitmap, username)
}