package com.tfandkusu.template.view.main

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tfandkusu.template.compose.home.HomeScreen
import com.tfandkusu.template.compose.info.InfoScreen
import com.tfandkusu.template.viewmodel.home.HomeViewModelImpl
import com.tfandkusu.template.viewmodel.info.InfoViewModelImpl

private const val HOME_PATH = "home"

private const val INFO_PATH = "info"

@ExperimentalMaterial3Api
@Composable
fun AppContent(callOssLicense: () -> Unit) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = HOME_PATH) {
        composable(HOME_PATH) {
            val viewModel = hiltViewModel<HomeViewModelImpl>()
            HomeScreen(viewModel, callInfoScreen = {
                navController.navigate(INFO_PATH)
            })
        }
        composable(INFO_PATH) {
            val viewModel = hiltViewModel<InfoViewModelImpl>()
            InfoScreen(viewModel, finish = {
                navController.popBackStack()
            }, callOssLicense = callOssLicense)
        }
    }
}
