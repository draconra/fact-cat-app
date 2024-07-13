package jp.speakbuddy.edisonandroidexercise.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.speakbuddy.edisonandroidexercise.di.DataStoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FactHistoryViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository?
) : ViewModel() {

    private val _factHistory = MutableStateFlow<List<String>>(emptyList())
    val factHistory: StateFlow<List<String>> = _factHistory.asStateFlow()

    init {
        loadFactHistory()
    }

    private fun loadFactHistory() {
        viewModelScope.launch {
            dataStoreRepository?.factHistory?.collect { history ->
                _factHistory.value = history
            }
        }
    }
}