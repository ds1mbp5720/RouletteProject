package com.example.rouletteproject.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.rouletteproject.MainViewModel
import com.example.rouletteproject.navigation.BottomNavigation
import com.example.rouletteproject.navigation.NavigationGraph

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigation(navController = navController) }
    ) {
        val mainViewModel: MainViewModel = viewModel()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ){
            NavigationGraph(
                navController = navController,
                mainViewModel = mainViewModel
            )
        }
    }
}