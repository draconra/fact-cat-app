package jp.speakbuddy.edisonandroidexercise.ui.fact

import androidx.annotation.StringRes
import jp.speakbuddy.edisonandroidexercise.R

sealed class FactUiState {
    data object Loading : FactUiState()
    data class Success(val fact: String, val showCatsDialog: Boolean) : FactUiState()
    data class NoData(@StringRes val messageResId: Int = R.string.no_fact_available) : FactUiState()
    data class Error(val error: String?) : FactUiState()
}