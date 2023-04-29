package com.waqar.topgames

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.waqar.topgames.Model.Game
import com.waqar.topgames.view.DetailedScreen
import com.waqar.topgames.view.HomeScreen
import com.waqar.topgames.view.Screen
import com.waqar.topgames.viewModel.MainViewModel

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController, mainViewModel = MainViewModel())
        }
        composable(route = Screen.Detail.route) {
            val result = navController.previousBackStackEntry?.savedStateHandle?.get<Game>("item")
            result?.let {
                DetailedScreen(navController = navController, result = result)
            }

        }

    }
}