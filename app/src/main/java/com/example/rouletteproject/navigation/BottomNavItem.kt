package com.example.rouletteproject.navigation

import com.example.rouletteproject.R

sealed class BottomNavItem(
    val title: Int,
    val icon: Int,
    val screenRoute: String
) {
    object Roulette : BottomNavItem(R.string.bottom_main, R.drawable.baseline_crisis_alert_24, MainDestination.ROULETTE)
    object ManageList : BottomNavItem(R.string.bottom_manage, R.drawable.baseline_format_list_bulleted_24, MainDestination.MANAGE)
}
