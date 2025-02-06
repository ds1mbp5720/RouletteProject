package com.example.rouletteproject.component.card

data class CardStateItem(
    var text: String,
    var isSelected: Boolean = false,
    var isRotated : Boolean = false // true: 뒷면, false: 앞면
)