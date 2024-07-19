package jp.speakbuddy.edisonandroidexercise.ui.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import jp.speakbuddy.edisonandroidexercise.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val historyIcon = ImageVector.vectorResource(id = R.drawable.ic_history)

    val title = when (currentRoute) {
        ScreenModel.NavigationItem.Home.route -> stringResource(id = R.string.home)
        ScreenModel.NavigationItem.History(historyIcon).route -> stringResource(id = R.string.history)
        ScreenModel.NavigationItem.Search.route -> stringResource(id = R.string.search)
        else -> stringResource(id = R.string.app_name)
    }

    val screensInHomeFromBottomNav = listOf(
        ScreenModel.NavigationItem.Home,
        ScreenModel.NavigationItem.History(historyIcon),
        ScreenModel.NavigationItem.Search
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) }
            )
        },
        bottomBar = {
            BottomNavigationBar(screens = screensInHomeFromBottomNav, navController = navController)
        }
    ) { innerPadding ->
        MainNavigation(
            navController = navController,
            contentPadding = innerPadding
        )
    }
}