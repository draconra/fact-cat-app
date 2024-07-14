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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import jp.speakbuddy.edisonandroidexercise.R
import jp.speakbuddy.edisonandroidexercise.ui.home.ErrorContent
import jp.speakbuddy.edisonandroidexercise.ui.home.LottieImageAnimation

@Composable
fun FactScreen(viewModel: FactViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

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
        when (uiState) {
            is FactUiState.Loading -> LottieImageAnimation()

            is FactUiState.Success -> {
                val state = uiState as FactUiState.Success
                FactBody(
                    fact = state.fact,
                    onUpdateFact = { viewModel.updateFact() }
                )
                if (state.showCatsDialog) {
                    CatsDialog(
                        fact = state.fact,
                        onDismiss = { viewModel.dismissCatsDialog() }
                    )
                }
            }

            is FactUiState.Error -> {
                val state = uiState as FactUiState.Error
                ErrorContent(error = state.error ?: "", onTryAgain = { viewModel.updateFact() })
            }

            is FactUiState.NoData -> ErrorContent(
                error = stringResource(id = R.string.no_fact_available),
                onTryAgain = { viewModel.updateFact() })
        }
    }
}