package jp.speakbuddy.edisonandroidexercise.ui

import android.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.unmockkAll
import jp.speakbuddy.edisonandroidexercise.di.DataStoreRepository
import jp.speakbuddy.edisonandroidexercise.network.FactResponse
import jp.speakbuddy.edisonandroidexercise.network.FactService
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
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

        Assert.assertEquals(expectedFact, viewModel.fact.getOrAwaitValue())
    }

    @Test
    fun `updateFact updates fact and saves it to DataStore`() = runTest {
        val newFact = "New Fact"
        coEvery { factService.getFact() } returns FactResponse(newFact, newFact.length)
        coEvery { dataStoreRepository.saveLastFact(newFact) } just Runs

        viewModel.updateFact()

        advanceUntilIdle()

        Assert.assertEquals(newFact, viewModel.fact.getOrAwaitValue())
        coVerify { dataStoreRepository.saveLastFact(newFact) }
    }

    @Test
    fun `updateFact sets error message when service fails`() = runTest {
        val errorMessage = "Service Error"
        coEvery { factService.getFact() } throws RuntimeException(errorMessage)

        viewModel.updateFact()

        advanceUntilIdle()

        val expectedErrorMessage = "Something went wrong. Error: $errorMessage"
        Assert.assertEquals(expectedErrorMessage, viewModel.fact.getOrAwaitValue())
    }
}