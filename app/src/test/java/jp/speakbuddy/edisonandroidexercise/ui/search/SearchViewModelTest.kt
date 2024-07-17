package jp.speakbuddy.edisonandroidexercise.ui.search

import android.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import jp.speakbuddy.edisonandroidexercise.model.FactListResponse
import jp.speakbuddy.edisonandroidexercise.model.FactResponse
import jp.speakbuddy.edisonandroidexercise.network.FactService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.Rule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val factService: FactService = mockk()

    private lateinit var viewModel: SearchViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = SearchViewModel(factService)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `searchFacts sets Success state when facts are found`() = runTest {
        val facts = listOf(
            FactResponse("Cat fact 1", 10),
            FactResponse("Another cat fact 2", 20)
        )
        coEvery { factService.getFacts(any(), any()) } returns FactListResponse(facts)

        viewModel.searchFacts("cat")

        advanceUntilIdle()

        val expectedState = SearchUiState.Success(facts)
        assertEquals(expectedState, viewModel.uiState.value)
    }

    @Test
    fun `searchFacts sets NoData state when no facts are found`() = runTest {
        val facts = listOf(
            FactResponse("Dog fact 1", 10),
            FactResponse("Another dog fact 2", 20)
        )
        coEvery { factService.getFacts(any(), any()) } returns FactListResponse(facts)

        viewModel.searchFacts("cat")

        advanceUntilIdle()

        val expectedState = SearchUiState.NoData("cat")
        assertEquals(expectedState, viewModel.uiState.value)
    }

    @Test
    fun `searchFacts sets Error state when service fails`() = runTest {
        val errorMessage = "Service Error"
        coEvery { factService.getFacts(any(), any()) } throws RuntimeException(errorMessage)

        viewModel.searchFacts("cat")

        advanceUntilIdle()

        val expectedState = SearchUiState.Error(errorMessage)
        assertEquals(expectedState, viewModel.uiState.value)
    }
}