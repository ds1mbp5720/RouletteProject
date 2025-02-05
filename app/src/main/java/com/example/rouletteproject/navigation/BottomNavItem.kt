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
    object ManageList : BottomNavItem(
        title = R.string.bottom_manage,
        icon = R.drawable.baseline_format_list_bulleted_24,
        screenRoute = MainDestination.MANAGE,
        page = 2
    )
}
