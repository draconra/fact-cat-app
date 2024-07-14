package jp.speakbuddy.edisonandroidexercise.ui.history

import android.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import jp.speakbuddy.edisonandroidexercise.di.DataStoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FactHistoryViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dataStoreRepository: DataStoreRepository = mockk()

    private lateinit var viewModel: FactHistoryViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `initial fact history is loaded from DataStore`() = runTest {
        val expectedFactHistory = listOf("Fact 1", "Fact 2")
        coEvery { dataStoreRepository.factHistory } returns flowOf(expectedFactHistory)

        viewModel = FactHistoryViewModel(dataStoreRepository)

        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assert(uiState is FactHistoryUiState.Success)
        assertEquals(expectedFactHistory, (uiState as FactHistoryUiState.Success).factHistory)
    }

    @Test
    fun `fact history is updated when new facts are added`() = runTest {
        val initialFacts = listOf("Fact 1")
        val updatedFacts = listOf("Fact 1", "Fact 2")

        val stateFlow = MutableStateFlow(initialFacts)
        coEvery { dataStoreRepository.factHistory } returns stateFlow

        viewModel = FactHistoryViewModel(dataStoreRepository)

        advanceUntilIdle()

        var uiState = viewModel.uiState.value
        assert(uiState is FactHistoryUiState.Success)
        assertEquals(initialFacts, (uiState as FactHistoryUiState.Success).factHistory)

        // Simulate adding a new fact
        stateFlow.value = updatedFacts

        advanceUntilIdle()

        uiState = viewModel.uiState.value
        assert(uiState is FactHistoryUiState.Success)
        assertEquals(updatedFacts, (uiState as FactHistoryUiState.Success).factHistory)
    }

    @Test
    fun `setFactHistory updates the UI state`() = runTest {
        val factHistory = listOf("Fact 1", "Fact 2")

        viewModel = FactHistoryViewModel(dataStoreRepository)
        viewModel.setFactHistory(factHistory)

        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assert(uiState is FactHistoryUiState.Success)
        assertEquals(factHistory, (uiState as FactHistoryUiState.Success).factHistory)
    }

    @Test
    fun `error state is set when data store fails`() = runTest {
        val errorMessage = "Data Store Error"
        coEvery { dataStoreRepository.factHistory } throws RuntimeException(errorMessage)

        viewModel = FactHistoryViewModel(dataStoreRepository)

        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assert(uiState is FactHistoryUiState.Error)
        assertEquals(errorMessage, (uiState as FactHistoryUiState.Error).message)
    }

    @Test
    fun `empty state is set when fact history is empty`() = runTest {
        coEvery { dataStoreRepository.factHistory } returns flowOf(emptyList())

        viewModel = FactHistoryViewModel(dataStoreRepository)

        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assert(uiState is FactHistoryUiState.Empty)
    }
}