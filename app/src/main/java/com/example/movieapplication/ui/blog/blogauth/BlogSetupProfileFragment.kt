package com.example.movieapplication.ui.blog.blogauth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.movieapplication.R
import com.example.movieapplication.data.remote.blog.auth.AuthBlogDataSource
import com.example.movieapplication.databinding.FragmentBlogSetupProfileBinding
import com.example.movieapplication.domain.blog.auth.AuthRepositoryImpl
import com.example.movieapplication.presentation.blog.auth.BlogAuthViewModel

class BlogSetupProfileFragment : Fragment(R.layout.fragment_blog_setup_profile) {
    private lateinit var binding: FragmentBlogSetupProfileBinding
    private val viewModel by viewModels<BlogAuthViewModel> {
        BlogAuthViewModel.BlogAuthViewModelFactory(AuthRepositoryImpl(AuthBlogDataSource()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBlogSetupProfileBinding.bind(view)
    }
}
