package jp.speakbuddy.edisonandroidexercise.fact

import jp.speakbuddy.edisonandroidexercise.corenetwork.FactService
import jp.speakbuddy.edisonandroidexercise.corenetwork.di.DataStoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class FakeLoadingFactViewModel(factService: FactService, dataStoreRepository: DataStoreRepository) : FactViewModel(factService, dataStoreRepository) {
    override val uiState: StateFlow<FactUiState> = MutableStateFlow(
        FactUiState.Loading)
}

class FakeSuccessFactViewModel(factService: FactService, dataStoreRepository: DataStoreRepository) : FactViewModel(factService, dataStoreRepository) {
    override val uiState: StateFlow<FactUiState> =
        MutableStateFlow(FactUiState.Success(fact = "A random fact", showCatsDialog = false))
}

class FakeErrorFactViewModel(factService: FactService, dataStoreRepository: DataStoreRepository) : FactViewModel(factService, dataStoreRepository) {
    override val uiState: StateFlow<FactUiState> =
        MutableStateFlow(FactUiState.Error(error = "An error occurred"))
}