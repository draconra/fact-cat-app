package jp.speakbuddy.edisonandroidexercise.fact

import jp.speakbuddy.edisonandroidexercise.di.DataStoreRepository
import jp.speakbuddy.edisonandroidexercise.network.FactService
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactUiState
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class FakeLoadingFactViewModel(factService: FactService, dataStoreRepository: DataStoreRepository) : FactViewModel(factService, dataStoreRepository) {
    override val uiState: StateFlow<FactUiState> = MutableStateFlow(FactUiState.Loading)
}

class FakeSuccessFactViewModel(factService: FactService, dataStoreRepository: DataStoreRepository) : FactViewModel(factService, dataStoreRepository) {
    override val uiState: StateFlow<FactUiState> =
        MutableStateFlow(FactUiState.Success(fact = "A random fact", showCatsDialog = false))
}

class FakeErrorFactViewModel(factService: FactService, dataStoreRepository: DataStoreRepository) : FactViewModel(factService, dataStoreRepository) {
    override val uiState: StateFlow<FactUiState> =
        MutableStateFlow(FactUiState.Error(error = "An error occurred"))
}