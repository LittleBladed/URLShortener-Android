package com.example.urlshortener.navigation

import HomeScreen
import LoadingScreen
import UrlShortenerViewModel
import HistoryScreen
import ResultsScreen
import android.os.Build
import Header
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.urlshortener.ui.AppViewModelProvider
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.unit.sp

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun NavGraph(navController: NavHostController) {
    val viewModel: UrlShortenerViewModel = viewModel(factory = AppViewModelProvider.Factory)

    NavHost(navController = navController, startDestination = NavigationEnums.HOME.name) {
        composable(NavigationEnums.HOME.name) {
            Header()
            HomeScreen(navController, viewModel)
        }

        composable(NavigationEnums.LOADING.name) {
            LoadingScreen(navController, viewModel)
        }

        composable(NavigationEnums.RESULTS.name) {
            ResultsScreen(navController, viewModel)
        }

        composable(NavigationEnums.HISTORY.name) {
            HistoryScreen(navController, viewModel)
        }
    }
}