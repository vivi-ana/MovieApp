package com.example.movieapplication.domain.blog.home

import com.example.movieapplication.core.Resource
import com.example.movieapplication.data.model.blog.Post
import com.example.movieapplication.data.remote.blog.home.RemoteBlogDataSourse
import com.example.movieapplication.domain.blog.home.BlogRepository

class BlogRepositoryImpl (private val dataSource: RemoteBlogDataSourse): BlogRepository {
    override suspend fun getLatestPosts(): Resource<List<Post>> = dataSource.getLatestPosts()
}