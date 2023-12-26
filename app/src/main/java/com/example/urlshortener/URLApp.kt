package com.example.urlshortener;

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.urlshortener.navigation.NavGraph


@Composable
fun URlApp() {
    var navCont : NavHostController = rememberNavController()

    NavGraph(navController = navCont)

}