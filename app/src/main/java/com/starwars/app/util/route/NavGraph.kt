package com.starwars.app.util.route

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.starwars.app.character.presentation.CharacterScreen
import com.starwars.app.starship.presentation.StarshipScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreen.StarshipScreen.route,
    ) {
        composable(route = AppScreen.CharacterScreen.route) {
            CharacterScreen(
                navController = navController
            )
        }
        composable(route = AppScreen.StarshipScreen.route) {
            StarshipScreen(
                navController = navController
            )
        }
    }
}