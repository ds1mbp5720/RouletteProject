package com.example.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Roulette")
data class RouletteEntity(
    @PrimaryKey( autoGenerate = true )
    val id: Long,
    val title: String, //todo 선택한거 표시 추가하기 (룰렛, 카드)
    val rouletteData: List<String> // 룰렛 요소 list
)