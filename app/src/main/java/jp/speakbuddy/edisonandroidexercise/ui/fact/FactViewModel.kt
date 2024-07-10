package jp.speakbuddy.edisonandroidexercise.ui.fact

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.speakbuddy.edisonandroidexercise.network.FactService
import jp.speakbuddy.edisonandroidexercise.di.DataStoreRepository
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

    init {
        viewModelScope.launch {
            val lastFact = dataStoreRepository?.lastFact?.firstOrNull()
            _fact.value = lastFact ?: ""
        }
    }

    fun updateFact() {
        viewModelScope.launch {
            try {
                val newFact = factService?.getFact()?.fact ?: "Preview Fact"
                _fact.value = newFact
                dataStoreRepository?.saveLastFact(newFact)
            } catch (e: Throwable) {
                _fact.value = "something went wrong. error = ${e.message}"
            }
        }
    }

    constructor() : this(null, null) {
        _fact.value = "Preview Fact"
    }
}
