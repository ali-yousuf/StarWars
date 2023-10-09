package com.starwars.app.util.route

sealed class AppScreen(val route: String) {
    object MainScreen : AppScreen(ConstantAppScreenName.MAIN_SCREEN)
    object CharacterScreen : AppScreen(ConstantAppScreenName.CHARACTER_SCREEN)
    object StarshipScreen : AppScreen(ConstantAppScreenName.STARSHIP_SCREEN)
}


object ConstantAppScreenName {
    const val MAIN_SCREEN = "main_screen"
    const val CHARACTER_SCREEN = "character_screen"
    const val STARSHIP_SCREEN = "starship_screen"
    const val DETAILS_SCREEN = "details_screen"
}