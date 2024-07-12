package jp.speakbuddy.edisonandroidexercise.ui.fact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.speakbuddy.edisonandroidexercise.di.DataStoreRepository
import jp.speakbuddy.edisonandroidexercise.network.FactResponse
import jp.speakbuddy.edisonandroidexercise.network.FactService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FactViewModel @Inject constructor(
    private val factService: FactService?,
    private val dataStoreRepository: DataStoreRepository?
) : ViewModel() {

    private val _fact = MutableStateFlow("No fact available")
    val fact: StateFlow<String> = _fact.asStateFlow()

    private val _length = MutableStateFlow(0)
    val length: StateFlow<Int> = _length.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _showCatsDialog = MutableStateFlow(false)
    val showCatsDialog: StateFlow<Boolean> = _showCatsDialog.asStateFlow()

    init {
        fetchLastFact()
    }

    private fun fetchLastFact() {
        viewModelScope.launch {
            setLoading(true)
            val lastFact = getLastFactFromDataStore()
            _fact.value = lastFact ?: "No fact available"
            setLoading(false)
        }
    }

    private suspend fun getLastFactFromDataStore(): String? {
        return dataStoreRepository?.lastFact?.firstOrNull()
    }

    fun updateFact() {
        viewModelScope.launch {
            setLoading(true)
            try {
                val response = fetchFactFromService()
                val newFact = response?.fact ?: "Preview Fact"
                updateFactState(newFact, response?.length ?: 0)
                saveFactToDataStore(newFact)
            } catch (e: Throwable) {
                _fact.value = "Something went wrong. Error: ${e.message}"
            }
            setLoading(false)
        }
    }

    private suspend fun fetchFactFromService(): FactResponse? {
        return factService?.getFact()
    }

    private fun updateFactState(newFact: String, length: Int) {
        _fact.value = newFact
        _length.value = length
        _showCatsDialog.value = newFact.contains("cats", ignoreCase = true)
    }

    private suspend fun saveFactToDataStore(fact: String) {
        dataStoreRepository?.saveLastFact(fact)
    }

    private fun setLoading(isLoading: Boolean) {
        _loading.value = isLoading
    }

    fun dismissCatsDialog() {
        _showCatsDialog.value = false
    }
}