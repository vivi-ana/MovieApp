package com.example.movieapplication.domain.blog

import com.example.movieapplication.core.Resource
import com.example.movieapplication.data.model.blog.Post

interface BlogRepository {
    suspend fun getLatestPosts(): Resource<List<Post>>
}