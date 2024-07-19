package jp.speakbuddy.edisonandroidexercise.history

sealed class FactHistoryUiState {
    data object Loading : FactHistoryUiState()
    data class Success(val factHistory: List<String>) : FactHistoryUiState()
    data class Error(val message: String) : FactHistoryUiState()
    data object Empty : FactHistoryUiState()
}