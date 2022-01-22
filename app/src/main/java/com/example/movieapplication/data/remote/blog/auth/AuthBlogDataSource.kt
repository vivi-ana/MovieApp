package com.example.movieapplication.data.remote.blog.auth

import android.graphics.Bitmap
import com.example.movieapplication.data.model.blog.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthBlogDataSource {
    suspend fun signIn(email:String, password:String): FirebaseUser?{
    val authResult = FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
    return authResult.user
    }
    suspend fun signUp(email: String, password: String, username: String):FirebaseUser?{
        val authResult = FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).await()
        authResult.user?.uid?.let { uid ->
            FirebaseFirestore.getInstance().collection("users").document(uid).set(User(email, username, "PHOTO_URL.PNG")).await()
        }
        return authResult.user
    }
    suspend fun updateUserProfile(imageBitmap:Bitmap, username: String){}
}