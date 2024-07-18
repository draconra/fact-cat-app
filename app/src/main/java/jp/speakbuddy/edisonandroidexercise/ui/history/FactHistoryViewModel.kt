package jp.speakbuddy.edisonandroidexercise.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.speakbuddy.edisonandroidexercise.corenetwork.di.DataStoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class FactHistoryViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository?
) : ViewModel() {

    private val _uiState = MutableStateFlow<FactHistoryUiState>(FactHistoryUiState.Loading)
    val uiState: StateFlow<FactHistoryUiState> = _uiState.asStateFlow()

    init {
        loadFactHistory()
    }

    private fun loadFactHistory() {
        viewModelScope.launch {
            try {
                _uiState.value = FactHistoryUiState.Loading
                dataStoreRepository?.factHistory?.collect { history ->
                    _uiState.value = if (history.isEmpty()) {
                        FactHistoryUiState.Empty
                    } else {
                        FactHistoryUiState.Success(history)
                    }
                }
            } catch (e: Exception) {
                _uiState.value = FactHistoryUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    // Method to set fact history for testing purposes
    fun setFactHistory(factHistory: List<String>) {
        _uiState.value = if (factHistory.isEmpty()) {
            FactHistoryUiState.Empty
        } else {
            FactHistoryUiState.Success(factHistory)
        }
    }
}