package jp.speakbuddy.edisonandroidexercise.ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import jp.speakbuddy.edisonandroidexercise.R
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactScreen
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: FactViewModel) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) },
                actions = {
                    IconButton(onClick = { navController.navigate(NavigationItem.Search.route) }) {
                        Icon(Icons.Default.Search, contentDescription = stringResource(id = R.string.search))
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = NavigationItem.Home.route, Modifier.padding(innerPadding)) {
            composable(NavigationItem.Home.route) { FactScreen(viewModel = viewModel) }
            composable(NavigationItem.History.route) {/*TODO Add History Screen in the next PR*/}
            composable(NavigationItem.About.route) {/*TODO Add About Screen in the the next PR*/}
            composable(NavigationItem.Search.route) {/*TODO Add About Screen in the the next PR*/}
        }
    }
}