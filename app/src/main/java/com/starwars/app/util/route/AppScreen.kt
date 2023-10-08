package com.starwars.app.util.route

sealed class AppScreen(val route: String) {
    object HomeScreen : AppScreen(ConstantAppScreenName.HOME_SCREEN)
    object CharacterScreen : AppScreen(ConstantAppScreenName.CHARACTER_SCREEN)
    object DetailsScreen : AppScreen(ConstantAppScreenName.DETAILS_SCREEN)
}


object ConstantAppScreenName {
    const val HOME_SCREEN = "home_screen"
    const val CHARACTER_SCREEN = "character_screen"
    const val DETAILS_SCREEN = "details_screen"
}