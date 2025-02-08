package com.example.rouletteproject.main

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.data.entity.RouletteEntity
import com.example.rouletteproject.MainViewModel
import com.example.rouletteproject.dialog.InsertRouletteListDialog
import com.example.rouletteproject.dialog.ListChoiceDialog
import com.example.rouletteproject.navigation.BottomNavigation
import com.example.rouletteproject.navigation.MainDestination
import com.example.rouletteproject.navigation.NavigationGraph

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var showInsertDialog by remember { mutableStateOf(false) }
    var showListChoiceDialog by remember { mutableStateOf(false) }
    val navController = rememberNavController()
    val mainViewModel: MainViewModel = viewModel()
    mainViewModel.getAllList()
    val rouletteLists = mainViewModel.rouletteList.observeAsState().value
    BackPressed()
    Scaffold(
        topBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        modifier = Modifier
                            .padding(start = 10.dp),
                        onClick = {
                            navController.navigate(MainDestination.MANAGE)
                        }) {
                        Icon(
                            imageVector = Icons.Filled.List,
                            contentDescription = "choice_list"
                        )
                    }
                    ChoiceView(title =
                        rouletteLists?.get(0)?.title ?: "테스트 리스트"
                    ) {
                        //todo 리스트 선택 Dialog 노출
                        showListChoiceDialog = true
                    }
                    IconButton(
                        modifier = Modifier
                            .padding(end = 10.dp),
                        onClick = {
                            //todo Move to SettingScreen
                        }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "app_setting"
                        )
                    }
                }
                Divider(
                    color = Color.LightGray,
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                )
            }
        },
        bottomBar = { BottomNavigation(navController = navController) },
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = {
                    showInsertDialog = true
                }) {
                Icon(
                    imageVector = Icons.Filled.Create,
                    contentDescription = "ManageListFloatingButton"
                )
            }
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            NavigationGraph(
                navController = navController,
                mainViewModel = mainViewModel
            )
            //todo navigation single top 고려
            if (showInsertDialog) {
                InsertRouletteListDialog(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp, vertical = 50.dp),
                    addRouletteList = { title, rouletteList ->
                        mainViewModel.insert(
                            RouletteEntity(
                                id = System.currentTimeMillis(),
                                title = title,
                                rouletteData = rouletteList
                            )
                        )
                    }
                ) {
                    showInsertDialog = false
                }
            }
            if (showListChoiceDialog) {
                //todo 리스트 선택항목 보여주는 부분
                ListChoiceDialog() {

                }
            }
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
            Toast.makeText(context, "한번 더 뒤로가기 클릭시 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}