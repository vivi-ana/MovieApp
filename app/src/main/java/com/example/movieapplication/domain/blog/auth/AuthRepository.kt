package com.example.movieapplication.domain.blog.auth

import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun signIn(email:String, password:String):FirebaseUser?
    suspend fun signUp(email: String, password: String, username:String): FirebaseUser?
    suspend fun updateProfile(imageBitmap: Bitmap,username: String)
}