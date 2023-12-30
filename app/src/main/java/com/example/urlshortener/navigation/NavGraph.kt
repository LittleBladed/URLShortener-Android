package com.example.urlshortener.navigation

import HomeScreen
import LoadingScreen
import UrlShortenerViewModel
import android.os.Build
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
            HomeScreen(navController, viewModel)
        }

        composable(NavigationEnums.LOADING.name) {
            LoadingScreen(navController, viewModel)
        }

        composable(NavigationEnums.RESULTS.name) {
            Text(text = "Results: ${viewModel.shortURL.collectAsState().value}")
        }

        composable(NavigationEnums.HISTORY.name) {
            // Collecting the state of the URL history Flow
            val urlHistory = viewModel.getURLHistory().collectAsState(initial = listOf()).value

            // Displaying each URL record in a list
            LazyColumn {
                items(urlHistory) { urlRecord ->
                    Text(
                        text = "Long URL: ${urlRecord.longURL}, Short URL: ${urlRecord.shortURL}",
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}