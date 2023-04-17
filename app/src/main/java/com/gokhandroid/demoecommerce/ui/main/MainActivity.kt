package com.gokhandroid.demoecommerce.ui.main

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.gokhandroid.demoecommerce.R
import com.gokhandroid.demoecommerce.base.BaseActivity
import com.gokhandroid.demoecommerce.databinding.ActivityMainBinding

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    private lateinit var navController: NavController

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navController = findNavController(R.id.main_navigation_fragment)
        NavigationUI.setupWithNavController(
            binding.bottomNav,
            navController
        )
    }

    override fun onSupportNavigateUp() =
        findNavController(R.id.main_navigation_fragment).navigateUp()

    fun clearBasket() {
        viewModel.deleteBasketProducts()
    }
}