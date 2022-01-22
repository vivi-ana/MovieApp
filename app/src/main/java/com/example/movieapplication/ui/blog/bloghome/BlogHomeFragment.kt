package com.example.movieapplication.ui.blog.bloghome

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.movieapplication.R
import com.example.movieapplication.core.Resource
import com.example.movieapplication.core.hide
import com.example.movieapplication.core.show
import com.example.movieapplication.data.remote.blog.home.RemoteBlogDataSourse
import com.example.movieapplication.databinding.FragmentBlogBinding
import com.example.movieapplication.domain.blog.home.BlogRepositoryImpl
import com.example.movieapplication.presentation.blog.home.BlogViewModel
import com.example.movieapplication.ui.blog.adapter.BlogHomeAdapter

class BlogHomeFragment : Fragment(R.layout.fragment_blog) {
    private lateinit var binding: FragmentBlogBinding
    private val viewModel by viewModels<BlogViewModel> {
        BlogViewModel.BlogViewModelFactory(
            BlogRepositoryImpl(
            RemoteBlogDataSourse()
        )
        )
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBlogBinding.bind(view)
        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_blogHomeFragment_to_movieFragment)
                }
            })
        viewModel.fetchLatestPost().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.show()
                }

                is Resource.Success -> {
                    binding.progressBar.hide()
                    binding.rvHome.adapter = BlogHomeAdapter(result.data)
                }

                is Resource.Failure -> {
                    binding.progressBar.hide()
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