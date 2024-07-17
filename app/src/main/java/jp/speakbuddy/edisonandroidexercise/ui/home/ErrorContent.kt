package jp.speakbuddy.edisonandroidexercise.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactTestTag

@Composable
fun ErrorContent(error: String, onTryAgain: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .testTag(FactTestTag.ERROR_CONTENT)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            LottieImageAnimation(rawRes = R.raw.cat_not_found, size = 150.dp)
            Text(
                text = error,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 16.dp),
            )
            Button(
                onClick = onTryAgain,
                modifier = Modifier.testTag(FactTestTag.TRY_AGAIN_BUTTON)
            ) {
                val buttonText =
                    if (error == stringResource(id = R.string.no_fact_available)) {
                        stringResource(id = R.string.update_fact)
                    } else {
                        stringResource(id = R.string.try_again)
                    }
                Text(text = buttonText)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewError() {
    ErrorContent("Error", onTryAgain = {})
}