package jp.speakbuddy.edisonandroidexercise.ui.fact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.speakbuddy.edisonandroidexercise.R
import jp.speakbuddy.edisonandroidexercise.di.DataStoreRepository
import jp.speakbuddy.edisonandroidexercise.model.FactResponse
import jp.speakbuddy.edisonandroidexercise.network.FactService
import jp.speakbuddy.edisonandroidexercise.util.containsCats
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class FactViewModel @Inject constructor(
    private val factService: FactService?,
    private val dataStoreRepository: DataStoreRepository?
) : ViewModel() {

    private val _uiState = MutableStateFlow<FactUiState>(FactUiState.Loading)
    open val uiState: StateFlow<FactUiState> = _uiState.asStateFlow()

    init {
        fetchLastFact()
    }

    private fun fetchLastFact() {
        viewModelScope.launch {
            _uiState.value = FactUiState.Loading
            val lastFact = getLastFactFromDataStore()
            _uiState.value = if (lastFact != null) {
                FactUiState.Success(lastFact, containsCats(lastFact))
            } else {
                FactUiState.NoData(R.string.no_fact_available)
            }
        }
    }

    private suspend fun getLastFactFromDataStore(): String? {
        return dataStoreRepository?.lastFact?.firstOrNull()
    }

    fun updateFact() {
        viewModelScope.launch {
            _uiState.value = FactUiState.Loading
            try {
                val response = fetchFactFromService()
                if (response != null) {
                    val newFact = response.fact
                    _uiState.value = FactUiState.Success(newFact, containsCats(newFact))
                    saveFactToDataStore(newFact)
                    addFactToHistory(newFact)
                } else {
                    _uiState.value = FactUiState.NoData(R.string.no_facts_available)
                }
            } catch (e: Throwable) {
                _uiState.value = FactUiState.Error(e.message)
            }
        }
    }

    private suspend fun fetchFactFromService(): FactResponse? {
        return factService?.getFact()
    }

    private suspend fun saveFactToDataStore(fact: String) {
        dataStoreRepository?.saveLastFact(fact)
    }

    private suspend fun addFactToHistory(fact: String) {
        dataStoreRepository?.addFactToHistory(fact)
    }

    fun dismissCatsDialog() {
        if (_uiState.value is FactUiState.Success) {
            _uiState.value = (_uiState.value as FactUiState.Success).copy(showCatsDialog = false)
        }
    }

    fun setUiState(state: FactUiState) {
        _uiState.value = state
    }
}