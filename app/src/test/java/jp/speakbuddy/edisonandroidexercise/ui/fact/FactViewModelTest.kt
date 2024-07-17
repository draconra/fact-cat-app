package jp.speakbuddy.edisonandroidexercise.ui.fact

import android.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.unmockkAll
import jp.speakbuddy.edisonandroidexercise.di.DataStoreRepository
import jp.speakbuddy.edisonandroidexercise.model.FactResponse
import jp.speakbuddy.edisonandroidexercise.network.FactService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class FactViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val factService: FactService = mockk()
    private val dataStoreRepository: DataStoreRepository = mockk()

    private lateinit var viewModel: FactViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        coEvery { dataStoreRepository.lastFact } returns flowOf("Stored Fact")

        viewModel = FactViewModel(factService, dataStoreRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `initial fact is retrieved from DataStore`() = runTest {
        val expectedFact = "Stored Fact"
        coEvery { dataStoreRepository.lastFact } returns flowOf(expectedFact)

        viewModel = FactViewModel(factService, dataStoreRepository)

        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assertTrue(uiState is FactUiState.Success)
        assertEquals(expectedFact, (uiState as FactUiState.Success).fact)
    }

    @Test
    fun `updateFact updates fact and saves it to DataStore`() = runTest {
        val newFact = "New Fact"
        val factResponse = FactResponse(newFact, newFact.length)
        coEvery { factService.getFact() } returns factResponse
        coEvery { dataStoreRepository.saveLastFact(newFact) } just Runs
        coEvery { dataStoreRepository.addFactToHistory(newFact) } just Runs

        viewModel.updateFact()

        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assertTrue(uiState is FactUiState.Success)
        assertEquals(newFact, (uiState as FactUiState.Success).fact)
        assertEquals(false, uiState.showCatsDialog)

        coVerify { dataStoreRepository.saveLastFact(newFact) }
        coVerify { dataStoreRepository.addFactToHistory(newFact) }
    }

    @Test
    fun `updateFact sets error message when service fails`() = runTest {
        val errorMessage = "Service Error"
        coEvery { factService.getFact() } throws RuntimeException(errorMessage)

        viewModel.updateFact()

        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assertTrue(uiState is FactUiState.Error)
        assertEquals(errorMessage, (uiState as FactUiState.Error).error)
    }

    @Test
    fun `updateFact shows cats dialog when fact contains cats`() = runTest {
        val newFact = "This fact contains cats"
        val factResponse = FactResponse(newFact, newFact.length)
        coEvery { factService.getFact() } returns factResponse
        coEvery { dataStoreRepository.saveLastFact(newFact) } just Runs
        coEvery { dataStoreRepository.addFactToHistory(newFact) } just Runs

        viewModel.updateFact()

        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assertTrue(uiState is FactUiState.Success)
        assertEquals(newFact, (uiState as FactUiState.Success).fact)
        assertEquals(true, uiState.showCatsDialog)
    }
}