package jp.speakbuddy.edisonandroidexercise.search

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import jp.speakbuddy.edisonandroidexercise.corenetwork.model.FactResponse
import jp.speakbuddy.edisonandroidexercise.history.FactHistoryCard

@Composable
fun SearchItem(facts: List<FactResponse>) {
    LazyColumn {
        items(facts) { fact ->
            FactHistoryCard(fact = fact.fact)
        }
    }
}