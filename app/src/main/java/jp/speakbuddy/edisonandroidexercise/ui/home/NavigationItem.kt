package jp.speakbuddy.edisonandroidexercise.ui.home

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector
import jp.speakbuddy.edisonandroidexercise.R

sealed class NavigationItem(
    val route: String,
    @StringRes val label: Int,
    val icon: ImageVector
) {
    object Home : NavigationItem("home", R.string.home, Icons.Default.Home)
    object History : NavigationItem("history", R.string.history, Icons.Default.Favorite)
    object About : NavigationItem("about", R.string.about, Icons.Default.Info)
}