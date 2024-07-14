package jp.speakbuddy.edisonandroidexercise.ui.history

sealed class FactHistoryUiState {
    object Loading : FactHistoryUiState()
    data class Success(val factHistory: List<String>) : FactHistoryUiState()
    data class Error(val message: String) : FactHistoryUiState()
    object Empty : FactHistoryUiState()
}