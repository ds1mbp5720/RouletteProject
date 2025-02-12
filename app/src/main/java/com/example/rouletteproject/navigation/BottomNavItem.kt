package com.example.rouletteproject.navigation

import com.example.rouletteproject.R

sealed class BottomNavItem(
    val title: Int,
    val icon: Int,
    val screenRoute: String,
    val page: Int
) {
    object Roulette : BottomNavItem(
        title = R.string.bottom_main,
        icon = R.drawable.baseline_crisis_alert_24,
        screenRoute = MainDestination.ROULETTE,
        page = 0
    )
    object Card : BottomNavItem(
        title = R.string.bottom_card,
        icon = R.drawable.baseline_grid_view_24,
        screenRoute = MainDestination.CARD,
        page = 1
    )
    object Ladder : BottomNavItem(
        title = R.string.bottom_ladder,
        icon = R.drawable.baseline_pattern_24,
        screenRoute = MainDestination.LADDER,
        page = 2
    )
}
