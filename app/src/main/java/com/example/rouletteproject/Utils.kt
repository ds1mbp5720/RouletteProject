package com.example.rouletteproject

import androidx.compose.ui.graphics.Color

fun randomColor(): Color {
    return when ((1..5).random()) {
        1 -> Color(0xFFD1FA84)
        2 -> Color(0xFFB4FFF4)
        3 -> Color(0xFFFFA9A9)
        4 -> Color(0xFF6579FF)
        else -> Color(0xFFFFA4F0)
    }
}