package jp.speakbuddy.edisonandroidexercise.ui.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun FactHistoryScreen(viewModel: FactHistoryViewModel = hiltViewModel()) {
    val factHistory by viewModel.factHistory.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Fact History") }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(factHistory) { fact ->
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = fact,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Divider()
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewScreen(){
    FactHistoryScreen()
}