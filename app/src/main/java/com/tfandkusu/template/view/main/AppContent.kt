package com.tfandkusu.template.view.main

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tfandkusu.template.compose.home.HomeScreen
import com.tfandkusu.template.viewmodel.home.HomeViewModelImpl

private const val HOME_PATH = "home"

@Composable
fun AppContent() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = HOME_PATH) {
        composable(HOME_PATH) {
            val viewModel = hiltViewModel<HomeViewModelImpl>()
            HomeScreen(viewModel)
        }
    }
}
