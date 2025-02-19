package com.example.rouletteproject.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.data.entity.RouletteEntity
import com.example.rouletteproject.MainViewModel
import com.example.rouletteproject.component.card.CardScreen
import com.example.rouletteproject.component.ladder.LadderScreen
import com.example.rouletteproject.component.roulette.RouletteScreen
import com.example.rouletteproject.managelist.ManageListScreen
import com.example.rouletteproject.setting.SettingDataStore
import com.example.rouletteproject.setting.SettingScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    selectedList: RouletteEntity?
) {
    NavHost(
        navController = navController,
        startDestination = SettingDataStore.getFirstScreen()
    ) {
        composable(BottomNavItem.Roulette.screenRoute) {
            RouletteScreen(
                mainViewModel = mainViewModel,
                selectedList = selectedList
            )
        }
        composable(BottomNavItem.Card.screenRoute) {
            CardScreen(
                mainViewModel = mainViewModel,
                selectedList = selectedList
            )
        }
        composable(BottomNavItem.Ladder.screenRoute) {
            LadderScreen(
                mainViewModel = mainViewModel,
                selectedList = selectedList
            )
        }
        composable(MainDestination.MANAGE) {
            ManageListScreen(
                mainViewModel = mainViewModel
            )
        }
        composable(MainDestination.SETTING) {
            SettingScreen(
                //mainViewModel = mainViewModel
            )
        }
    }
}

@Composable
fun BottomNavigation(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Roulette,
        BottomNavItem.Card,
        BottomNavItem.Ladder
    )

    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color(0xFF3F414E)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = stringResource(id = item.title),
                        modifier = Modifier
                            .width(26.dp)
                            .height(26.dp)
                    )
                },
                label = { Text(stringResource(id = item.title), fontSize = 9.sp) },
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = Color.Gray,
                selected = currentRoute == item.screenRoute,
                alwaysShowLabel = false,
                onClick = {
                    navController.navigate(item.screenRoute) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}