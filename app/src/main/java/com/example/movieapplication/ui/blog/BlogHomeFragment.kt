package com.example.movieapplication.ui.blog

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.movieapplication.R
import com.example.movieapplication.core.Resource
import com.example.movieapplication.data.remote.blog.RemoteBlogDataSourse
import com.example.movieapplication.databinding.FragmentBlogBinding
import com.example.movieapplication.domain.blog.BlogRepositoryImpl
import com.example.movieapplication.presentation.blog.BlogViewModel
import com.example.movieapplication.ui.blog.adapter.BlogHomeAdapter

class BlogHomeFragment : Fragment(R.layout.fragment_blog) {
    private lateinit var binding: FragmentBlogBinding
    private val viewModel by viewModels<BlogViewModel> {
        BlogViewModel.BlogViewModelFactory(BlogRepositoryImpl(
            RemoteBlogDataSourse()
        ))
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBlogBinding.bind(view)
        viewModel.fetchLatestPost().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvHome.adapter = BlogHomeAdapter(result.data)
                }

                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Hubo un error : ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
}