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
import jp.speakbuddy.edisonandroidexercise.fact.FactScreen
import jp.speakbuddy.edisonandroidexercise.history.FactHistoryScreen
import jp.speakbuddy.edisonandroidexercise.search.SearchScreen

@Composable
fun MainNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues
) {
    val historyIcon = ImageVector.vectorResource(id = R.drawable.ic_history)
    NavHost(
        navController,
        startDestination = ScreenModel.NavigationItem.Home.route,
        modifier.padding(contentPadding)
    ) {
        composable(ScreenModel.NavigationItem.Home.route) { FactScreen() }
        composable(ScreenModel.NavigationItem.History(historyIcon).route) { FactHistoryScreen() }
        composable(ScreenModel.NavigationItem.Search.route) { SearchScreen() }
    }
}