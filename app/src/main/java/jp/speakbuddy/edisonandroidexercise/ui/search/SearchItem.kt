package jp.speakbuddy.edisonandroidexercise.ui.search

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import jp.speakbuddy.edisonandroidexercise.model.FactResponse
import jp.speakbuddy.edisonandroidexercise.ui.history.FactHistoryCard

    @Composable
    fun SearchItem(facts: List<FactResponse>) {
        LazyColumn {
            items(facts) { fact ->
                FactHistoryCard(fact = fact.fact)
            }
        }
    }