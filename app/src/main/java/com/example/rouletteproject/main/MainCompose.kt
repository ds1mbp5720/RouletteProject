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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.data.entity.RouletteEntity
import com.example.rouletteproject.MainViewModel
import com.example.rouletteproject.R
import com.example.rouletteproject.component.AddIconButton
import com.example.rouletteproject.component.ThemeIconButton
import com.example.rouletteproject.dialog.BasicDivider
import com.example.rouletteproject.dialog.EditListDialog
import com.example.rouletteproject.dialog.InsertRouletteListDialog
import com.example.rouletteproject.navigation.BottomNavigation
import com.example.rouletteproject.navigation.MainDestination
import com.example.rouletteproject.navigation.NavigationGraph

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val context = LocalContext.current
    val mainViewModel: MainViewModel = viewModel()
    val rouletteLists = mainViewModel.rouletteList.observeAsState().value
    var selectedList: RouletteEntity? by remember { mutableStateOf(rouletteLists?.get(0)) }
    var firstScreen by remember { mutableStateOf(true) }
    var showDropdownMenu by remember { mutableStateOf(false) }
    var showInsertDialog by remember { mutableStateOf(false) }
    var showEditListDialog by remember { mutableStateOf(false) }
    val navController = rememberNavController()

    LaunchedEffect(key1 = rouletteLists) {
        if (rouletteLists != null) {
            if (firstScreen) {
                selectedList = rouletteLists[0]
                firstScreen = false
            }
            val selectedInRouletteList = rouletteLists.find { it.id == selectedList?.id }
            if (selectedInRouletteList != null) {
                if (selectedInRouletteList != selectedList) {
                    selectedList = selectedInRouletteList
                }
            }
        }
    }
    BackPressed()
    Scaffold(
        topBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ThemeIconButton(
                        modifier = Modifier,
                        onClick = {
                            if (navController.currentDestination?.route == MainDestination.MANAGE || navController.currentDestination?.route == MainDestination.SETTING)
                                navController.popBackStack()
                            navController.navigate(MainDestination.MANAGE)
                            navOptions {
                                launchSingleTop = true
                                popUpTo(MainDestination.SETTING) {
                                    inclusive = true
                                }
                            }
                        }) {
                        Icon(
                            modifier = Modifier
                                .size(48.dp)
                                .padding(start = 4.dp),
                            imageVector = Icons.Filled.List,
                            contentDescription = "choice_list"
                        )
                    }
                    ExposedDropdownMenuBox(
                        expanded = showDropdownMenu,
                        onExpandedChange = {
                            showDropdownMenu = !showDropdownMenu
                        },
                        modifier = Modifier
                            .padding(horizontal = 2.dp, vertical = 10.dp)
                    ) {
                        Card(
                            modifier = Modifier,
                            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(corner = CornerSize(16.dp))
                        ) {
                            BasicTextField(
                                value = selectedList?.title ?: stringResource(id = R.string.text_none_list),
                                onValueChange = {},
                                readOnly = true,
                                textStyle = TextStyle(
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                ),
                                singleLine = true,
                                modifier = Modifier
                                    .padding(start = 16.dp, top = 6.dp, bottom = 6.dp)
                                    .menuAnchor(),
                            ) { innerTextField ->
                                Row(
                                    modifier = Modifier.padding(0.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    innerTextField()
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = showDropdownMenu)
                                }
                            }
                        }
                        ExposedDropdownMenu(
                            expanded = showDropdownMenu,
                            onDismissRequest = { showDropdownMenu = false }) {
                            rouletteLists?.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(text = item.title) },
                                    onClick = {
                                        selectedList = item
                                        showDropdownMenu = false
                                    }
                                )
                            }
                            BasicDivider(paddingVertical = 1)
                            AddIconButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.CenterHorizontally)
                            ) {
                                showInsertDialog = true
                            }
                        }
                    }
                    ThemeIconButton(
                        modifier = Modifier,
                        onClick = {
                            if (navController.currentDestination?.route == MainDestination.MANAGE || navController.currentDestination?.route == MainDestination.SETTING)
                                navController.popBackStack()
                            navController.navigate(MainDestination.SETTING)
                            navOptions {
                                launchSingleTop = true
                                popUpTo(MainDestination.MANAGE) {
                                    inclusive = true
                                }
                            }
                        }) {
                        Icon(
                            modifier = Modifier
                                .size(48.dp)
                                .padding(end = 4.dp),
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
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = {
                    showEditListDialog = true
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
                mainViewModel = mainViewModel,
                selectedList = selectedList
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
            if (showEditListDialog) {
                if (selectedList != null) {
                    EditListDialog(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 50.dp, vertical = 50.dp),
                        mainViewModel = mainViewModel,
                        itemList = selectedList!!
                    ) {
                        showEditListDialog = false
                    }
                } else {
                    Toast.makeText(context, context.getString(R.string.text_none_select_list), Toast.LENGTH_SHORT).show()
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