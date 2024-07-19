package jp.speakbuddy.edisonandroidexercise.ui.home

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(
    screens: List<ScreenModel.NavigationItem>,
    navController: NavController
) {
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        screens.forEach { screens ->
            val currentRoute = navBackStackEntry?.destination?.route
            val selected = currentRoute == screens.route
            BottomNavigationItem(
                icon = {
                    Icon(
                        screens.icon,
                        contentDescription = stringResource(id = screens.label),
                        tint = if (selected) Color.White else Color.Black
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = screens.label),
                        color = if (selected) Color.White else Color.Black
                    )
                },
                selected = currentRoute == screens.route,
                onClick = {
                    navController.navigate(screens.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}