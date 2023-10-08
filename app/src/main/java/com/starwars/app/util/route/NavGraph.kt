package com.starwars.app.util.route

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.starwars.app.character.presentation.CharacterScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreen.CharacterScreen.route,
    ) {
        composable(route = AppScreen.CharacterScreen.route) {
            CharacterScreen(
                navController = navController
            )
        }
    }
}