package jp.speakbuddy.edisonandroidexercise.ui.fact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.speakbuddy.edisonandroidexercise.di.DataStoreRepository
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
        viewModelScope.launch {
            _loading.value = true
            val lastFact = dataStoreRepository?.lastFact?.firstOrNull()
            _fact.value = lastFact ?: "No fact available"
            _loading.value = false
        }
    }

    fun updateFact() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = factService?.getFact()
                val newFact = response?.fact ?: "Preview Fact"
                _fact.value = newFact
                _length.value = response?.length ?: 0
                _showCatsDialog.value = newFact.contains("cats", ignoreCase = true)
                dataStoreRepository?.saveLastFact(newFact)
            } catch (e: Throwable) {
                _fact.value = "Something went wrong. Error: ${e.message}"
            }
            _loading.value = false
        }
    }

    fun dismissCatsDialog() {
        _showCatsDialog.value = false
    }
}