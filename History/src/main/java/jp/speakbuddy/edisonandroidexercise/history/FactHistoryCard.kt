package jp.speakbuddy.edisonandroidexercise.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.speakbuddy.edisonandroidexercise.coreui.util.containsCats
import jp.speakbuddy.edisonandroidexercise.coreui.util.isLongFact
import jp.speakbuddy.edisonandroidexercise.coreui.R as coreR

@Composable
fun FactHistoryCard(fact: String) {
    val factLength = fact.length
    val isLong = isLongFact(fact)
    val containsCats = containsCats(fact)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = fact,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .testTag(FactHistoryTestTags.FACT_TEXT)
            )
            if (isLong) {
                Text(
                    text = stringResource(id = coreR.string.fact_length, factLength),
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .testTag(FactHistoryTestTags.FACT_LENGTH)
                )
            }
            if (containsCats) {
                Text(
                    text = stringResource(id = coreR.string.multiple_cats),
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .testTag(FactHistoryTestTags.MULTIPLE_CATS)
                )
            }
        }
    }
}

@Composable
@Preview
fun PreviewHistory() {
    FactHistoryCard(fact = "Nyan")
}