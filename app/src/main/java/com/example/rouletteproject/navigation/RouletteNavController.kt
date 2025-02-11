package com.example.rouletteproject.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

object MainDestination {
    const val ROULETTE = "roulette"
    const val CARD = "card"
    const val LADDER = "ladder"
    const val MANAGE = "manage"
}

@Composable
fun rememberRouletteNavController(
    navController: NavHostController = rememberNavController()
): RouletteNavController = remember(navController) {
    RouletteNavController(navController)
}

@Stable
class  RouletteNavController(
    val navController: NavHostController
) {
    val currentRoute: String? get() = navController.currentDestination?.route

    fun upPress() {
        navController.navigateUp()
    }
}
