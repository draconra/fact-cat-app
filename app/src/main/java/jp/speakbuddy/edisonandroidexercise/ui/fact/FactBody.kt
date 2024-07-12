package jp.speakbuddy.edisonandroidexercise.ui.fact

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.speakbuddy.edisonandroidexercise.R
import jp.speakbuddy.edisonandroidexercise.ui.home.LottieImageAnimation
import jp.speakbuddy.edisonandroidexercise.util.isLongFact

@Composable
fun FactBody(
    fact: String,
    onUpdateFact: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LottieImageAnimation(rawRes = R.raw.cat_write, size = 150.dp)
        Text(
            text = fact,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        if (isLongFact(fact)) {
            Text(
                text = stringResource(id = R.string.fact_length, fact.length),
                style = MaterialTheme.typography.bodySmall
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
        Button(onClick = onUpdateFact) {
            Text(text = stringResource(R.string.update_fact))
        }
    }
}

@Preview
@Composable
private fun Preview() {
    FactBody(fact = stringResource(id = R.string.fact), onUpdateFact = {})
}