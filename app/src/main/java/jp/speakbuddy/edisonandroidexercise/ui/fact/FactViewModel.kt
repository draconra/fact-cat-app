package jp.speakbuddy.edisonandroidexercise.ui.fact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.speakbuddy.edisonandroidexercise.di.DataStoreRepository
import jp.speakbuddy.edisonandroidexercise.network.FactService
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class FactViewModel @Inject constructor(
    private val factService: FactService?,
    private val dataStoreRepository: DataStoreRepository?
) : ViewModel() {

    private val _fact = MutableLiveData<String>()
    val fact: LiveData<String> = _fact

    private val _length = MutableLiveData<Int>()
    val length: LiveData<Int> = _length

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

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
                val newFact = factService?.getFact()?.fact ?: "Preview Fact"
                _fact.value = newFact
                _length.value = factService?.getFact()?.fact?.length ?: 0
                dataStoreRepository?.saveLastFact(newFact)
            } catch (e: Throwable) {
                _fact.value = "Something went wrong. Error: ${e.message}"
            }
            _loading.value = false
        }
    }
}
