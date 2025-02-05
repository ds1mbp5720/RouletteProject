package com.example.rouletteproject.component.card

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.example.rouletteproject.MainViewModel

@Composable
fun CardScreen(
    mainViewModel: MainViewModel
) {
    val rouletteLists = mainViewModel.rouletteList.observeAsState().value
    
    Text(text = "card screen")
}