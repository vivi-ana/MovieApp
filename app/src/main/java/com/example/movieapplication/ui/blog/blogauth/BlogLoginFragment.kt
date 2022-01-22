package com.example.movieapplication.ui.blog.blogauth


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.movieapplication.R
import com.example.movieapplication.core.Resource
import com.example.movieapplication.core.hide
import com.example.movieapplication.core.show
import com.example.movieapplication.data.remote.blog.auth.AuthBlogDataSource
import com.example.movieapplication.databinding.FragmentBlogLoginBinding
import com.example.movieapplication.domain.blog.auth.AuthRepository
import com.example.movieapplication.domain.blog.auth.AuthRepositoryImpl
import com.example.movieapplication.presentation.blog.auth.BlogAuthViewModel
import com.google.firebase.auth.FirebaseAuth

class BlogLoginFragment : Fragment(R.layout.fragment_blog_login) {
    private lateinit var binding: FragmentBlogLoginBinding
    private val viewModel by viewModels<BlogAuthViewModel> {
        BlogAuthViewModel.BlogAuthViewModelFactory(AuthRepositoryImpl(AuthBlogDataSource()))
    }
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBlogLoginBinding.bind(view)
        isUserLoggedIn()
        doLogin()
        goToSignUpPage()
    }
    private fun isUserLoggedIn(){ //ver si esta logueado o no
        firebaseAuth.currentUser?.let {
            findNavController().navigate(R.id.action_blogLoginFragment_to_blogHomeFragment)
        }
    }
    private fun doLogin(){
        binding.btnSignin.setOnClickListener {
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            validateCredentials(email, password)
            signIn(email, password)
        }
    }
    private fun goToSignUpPage(){
        binding.txtSignup.setOnClickListener {
            findNavController().navigate(R.id.action_blogLoginFragment_to_blogRegisterFragment)
        }
    }
    private fun validateCredentials(email: String, password: String){
        if (email.isEmpty()){
            binding.editTextEmail.error = "E-mail is empty"
            return
        }
        if (password.isEmpty()){
            binding.editTextEmail.error = "E-mail is empty"
            return
        }
    }
    private fun signIn(email: String, password: String){
        viewModel.signIn(email, password).observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Resource.Loading -> {
                    binding.progressBar.show()
                    binding.btnSignin.isEnabled = false
                }
                is Resource.Success -> {
                    binding.progressBar.hide()
                    findNavController().navigate(R.id.action_blogLoginFragment_to_blogHomeFragment)
                    Toast.makeText(requireContext(), "Welcome ${result.data?.email}", Toast.LENGTH_SHORT).show()
                }
                is Resource.Failure -> {
                    binding.progressBar.hide()
                    binding.btnSignin.isEnabled = true
                    Toast.makeText(requireContext(), "Error: ${result.exception}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}