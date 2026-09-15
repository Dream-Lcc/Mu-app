package com.campus.mu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.campus.mu.ui.theme.MuTheme
import com.campus.mu.screen.BillScreen
import com.campus.mu.screen.HomeScreen
import com.campus.mu.screen.MineScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MuTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val current = navBackStackEntry?.destination
                            val items = listOf(
                                Triple("首页", "home", Icons.Filled.WaterDrop),
                                Triple("账单", "bill", Icons.Filled.Receipt),
                                Triple("我的", "mine", Icons.Filled.Person)
                            )
                            items.forEach { (label, route, icon) ->
                                NavigationBarItem(
                                    selected = current?.hierarchy?.any { it.route == route } == true,
                                    onClick = {
                                        navController.navigate(route) {
                                            popUpTo("home") { saveState = true }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    icon = { Icon(icon, contentDescription = label) },
                                    label = { Text(label) }
                                )
                            }
                        }
                    }
                ) { pad ->
                    NavHost(navController, startDestination = "home", Modifier.padding(pad)) {
                        composable("home") { HomeScreen() }
                        composable("bill") { BillScreen() }
                        composable("mine") { MineScreen() }
                    }
                }
            }
        }
    }
}
