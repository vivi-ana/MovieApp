package com.example.movieapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.movieapplication.R
import com.example.movieapplication.core.hide
import com.example.movieapplication.core.show
import com.example.movieapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        observeDestinationChange()
    }

    private fun observeDestinationChange() {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.blogLoginFragment -> {
                    binding.bottomNavigationView.hide()
                }
                R.id.blogRegisterFragment -> {
                    binding.bottomNavigationView.hide()
                }
                R.id.blogSetupProfileFragment -> {
                    binding.bottomNavigationView.hide()
                }
                R.id.movieFragment -> {
                    binding.bottomNavigationView.hide()
                }
                R.id.movieDetailFragment -> {
                    binding.bottomNavigationView.hide()
                }
                else -> {
                    binding.bottomNavigationView.show()
                }
            }
        }
    }
}