package jp.speakbuddy.edisonandroidexercise.ui.home

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import jp.speakbuddy.edisonandroidexercise.R

sealed class NavigationItem(
    val route: String,
    @StringRes val label: Int,
    val icon: ImageVector
) {
    data object Home : NavigationItem("home", R.string.home, Icons.Default.Home)
    data class History(val iconHistory: ImageVector) :
        NavigationItem("history", R.string.history, iconHistory)
    data object Search : NavigationItem("search", R.string.search, Icons.Default.Search)
}