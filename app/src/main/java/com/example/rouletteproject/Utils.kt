package com.example.rouletteproject

import androidx.compose.ui.graphics.Color

fun randomColor(): Color {
    return when ((1..10).random()) {
        1 -> Color(0xFFD1FA84)
        2 -> Color(0xFFB4FFF4)
        3 -> Color(0xFFFFA9A9)
        4 -> Color(0xFF6579FF)
        5 -> Color(0xFFC788E9)
        6 -> Color(0xFFE9DC86)
        7 -> Color(0xFF89F77D)
        8 -> Color(0xE6F2DDFF)
        9 -> Color(0xFFCACACA)
        else -> Color(0xFFFFA4F0)
    }
}