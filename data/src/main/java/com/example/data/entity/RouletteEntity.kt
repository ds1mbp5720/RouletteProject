package com.example.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Roulette")
data class RouletteEntity(
    @PrimaryKey( autoGenerate = true )
    val id: Long,
    val title: String,
    val rouletteData: List<String> // 룰렛 요소 list
)