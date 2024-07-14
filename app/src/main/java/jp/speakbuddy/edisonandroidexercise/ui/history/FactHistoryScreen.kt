package jp.speakbuddy.edisonandroidexercise.ui.history

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch

@Composable
fun FactHistoryScreen(viewModel: FactHistoryViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Scroll to the top when the screen is composed
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            listState.scrollToItem(0)
        }
    }

    when (uiState) {
        is FactHistoryUiState.Loading -> {
            // Display a loading indicator
        }
        is FactHistoryUiState.Success -> {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .testTag(FactHistoryTestTags.FACT_HISTORY_LIST)
            ) {
                items((uiState as FactHistoryUiState.Success).factHistory) { fact ->
                    FactHistoryCard(fact = fact)
                }
            }
        }
        is FactHistoryUiState.Error -> {
            // Display an error message
        }
        is FactHistoryUiState.Empty -> {
            NoFactsFound()
        }
    }
}