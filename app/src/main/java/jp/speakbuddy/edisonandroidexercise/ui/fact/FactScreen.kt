package jp.speakbuddy.edisonandroidexercise.ui.fact

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import jp.speakbuddy.edisonandroidexercise.ui.home.LottieLoadingAnimation

@Composable
fun FactScreen(
    viewModel: FactViewModel = hiltViewModel()
) {
    val fact by viewModel.fact.collectAsState()
    val length by viewModel.length.collectAsState()
    val loading by viewModel.loading.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 5.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        if (loading) {
            LottieLoadingAnimation()
        } else {
            FactBody(fact = fact, length = length, onUpdateFact = { viewModel.updateFact() })
        }
    }
}