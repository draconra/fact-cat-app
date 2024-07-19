package jp.speakbuddy.edisonandroidexercise.fact

import androidx.annotation.StringRes

sealed class FactUiState {
    data object Loading : FactUiState()
    data class Success(val fact: String, val showCatsDialog: Boolean) : FactUiState()
    data class NoData(@StringRes val messageResId: Int = R.string.no_fact_available) : FactUiState()
    data class Error(val error: String?) : FactUiState()
}