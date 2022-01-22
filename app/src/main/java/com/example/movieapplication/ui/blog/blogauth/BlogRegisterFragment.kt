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
import com.example.movieapplication.databinding.FragmentBlogRegisterBinding
import com.example.movieapplication.domain.blog.auth.AuthRepositoryImpl
import com.example.movieapplication.presentation.blog.auth.BlogAuthViewModel

class BlogRegisterFragment : Fragment(R.layout.fragment_blog_register) {
    private lateinit var binding: FragmentBlogRegisterBinding
    private val viewModel by viewModels<BlogAuthViewModel> {
        BlogAuthViewModel.BlogAuthViewModelFactory(AuthRepositoryImpl(AuthBlogDataSource()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBlogRegisterBinding.bind(view)
        signUp()
    }
    private fun signUp(){
        binding.btnSignup.setOnClickListener {
            val username = binding.editUsername.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            val confirmPassword = binding.editTextConfirmPassword.text.toString().trim()
            val email = binding.editTextEmail.text.toString().trim()
            if (validateUserData(password,confirmPassword,username,email))return@setOnClickListener
            createUser(email,password,username)
        }
    }
    private fun createUser(email:String, password:String, username:String){
        viewModel.signUp(email, password, username).observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Resource.Loading -> {
                    binding.progressBar.show()
                    binding.btnSignup.isEnabled = false
                }
                is Resource.Success -> {
                    binding.progressBar.hide()
                    findNavController().navigate(R.id.action_blogRegisterFragment_to_blogHomeFragment)
                }
                is Resource.Failure -> {
                    binding.progressBar.hide()
                    binding.btnSignup.isEnabled = true
                    Toast.makeText(requireContext(), "Error ${result.exception}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    private fun validateUserData(password: String, confirmPassword:String, username: String, email: String):Boolean {
        if (password != confirmPassword){
            binding.editTextConfirmPassword.error = "Password does not match"
            binding.editTextPassword.error = "Password does not match"
            return true
        }
        if (username.isEmpty()){
            binding.editUsername.error = "Username is empty"
            return true
        }
        if (email.isEmpty()){
            binding.editTextEmail.error = "Email is empty"
            return true
        }
        if (password.isEmpty()){
            binding.editTextPassword.error = "Password is empty"
            return true
        }
        if (confirmPassword.isEmpty()){
            binding.editUsername.error = "Confirm password is empty"
            return true
        }
        return false
    }
}