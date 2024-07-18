package jp.speakbuddy.edisonandroidexercise.search

import jp.speakbuddy.edisonandroidexercise.corenetwork.model.FactResponse

sealed class SearchUiState {
    data object Idle : SearchUiState()
    data object Loading : SearchUiState()
    data class Success(val facts: List<FactResponse>) : SearchUiState()
    data class NoData(val message: String) : SearchUiState()
    data class Error(val error: String?) : SearchUiState()
}