package com.example.urlshortener.navigation

import HomeScreen
import LoadingScreen
import UrlShortenerViewModel
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun NavGraph(navController: NavHostController) {
    val viewModel: UrlShortenerViewModel = viewModel()

    NavHost(navController = navController, startDestination = NavigationEnums.HOME.name) {
        composable(NavigationEnums.HOME.name) {
            HomeScreen(navController, viewModel)
        }

        composable(NavigationEnums.LOADING.name) {
            LoadingScreen(navController, viewModel)
        }

        composable(NavigationEnums.RESULTS.name) {
            Text(text = "Results: ${viewModel.shortURL.collectAsState().value}")
        }
    }
}