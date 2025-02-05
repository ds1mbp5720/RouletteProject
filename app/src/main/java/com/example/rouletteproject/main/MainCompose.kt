package com.example.rouletteproject.main

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.rouletteproject.MainViewModel
import com.example.rouletteproject.navigation.BottomNavigation
import com.example.rouletteproject.navigation.NavigationGraph

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    BackPressed()
    Scaffold(
        bottomBar = { BottomNavigation(navController = navController) }
    ) {
        val mainViewModel: MainViewModel = viewModel()
        mainViewModel.getAllList()
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

@Composable
fun BackPressed() {
    val context = LocalContext.current
    var backPressedState by remember { mutableStateOf(true) }
    var backPressedTime = 0L

    BackHandler(enabled = backPressedState) {
        if (System.currentTimeMillis() - backPressedTime <= 500L) {
            (context as Activity).finish()
        } else {
            backPressedState = true
            Toast.makeText(context, "한번 더 뒤로가기 클릭시 앱이 종료됩니다.",Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}