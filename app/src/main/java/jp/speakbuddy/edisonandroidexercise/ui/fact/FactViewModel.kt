package jp.speakbuddy.edisonandroidexercise.ui.fact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.speakbuddy.edisonandroidexercise.network.FactService
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class FactViewModel @Inject constructor(
    private val factService: FactService?
) : ViewModel() {

    protected val _fact = MutableLiveData<String>()
    val fact: LiveData<String> = _fact

    fun updateFact() {
        viewModelScope.launch {
            try {
                val newFact = factService?.getFact()?.fact ?: "Preview Fact"
                _fact.value = newFact
            } catch (e: Throwable) {
                _fact.value = "something went wrong. error = ${e.message}"
            }
        }
    }

    constructor() : this(null) {
        _fact.value = "Preview Fact"
    }
}


