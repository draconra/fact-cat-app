package jp.speakbuddy.edisonandroidexercise.ui.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.speakbuddy.edisonandroidexercise.R
import jp.speakbuddy.edisonandroidexercise.ui.home.LottieImageAnimation

@Composable
fun NoFactsFound() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag(FactHistoryTestTags.NO_FACTS_FOUND),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.no_facts_available).uppercase(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
            )
            LottieImageAnimation(
                rawRes = R.raw.cat_not_found,
                size = 250.dp
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewNoFact() {
    NoFactsFound()
}