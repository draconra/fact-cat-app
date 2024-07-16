package jp.speakbuddy.edisonandroidexercise.ui.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import jp.speakbuddy.edisonandroidexercise.R
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactScreen
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactViewModel
import jp.speakbuddy.edisonandroidexercise.ui.history.FactHistoryScreen
import jp.speakbuddy.edisonandroidexercise.ui.search.SearchScreen

@Composable
fun MainNavigation(
    navController: NavHostController,
    viewModel: FactViewModel,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues
) {
    val historyIcon = ImageVector.vectorResource(id = R.drawable.ic_history)
    NavHost(
        navController,
        startDestination = NavigationItem.Home.route,
        modifier.padding(contentPadding)
    ) {
        composable(NavigationItem.Home.route) { FactScreen(viewModel = viewModel) }
        composable(NavigationItem.History(historyIcon).route) { FactHistoryScreen() }
        composable(NavigationItem.About.route) { /* TODO Add About Screen in the next PR */ }
        composable(NavigationItem.Search.route) { SearchScreen() }
    }
}