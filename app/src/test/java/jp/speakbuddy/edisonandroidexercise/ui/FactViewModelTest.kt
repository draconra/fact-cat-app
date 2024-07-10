package jp.speakbuddy.edisonandroidexercise.ui

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.*
import jp.speakbuddy.edisonandroidexercise.network.FactResponse
import jp.speakbuddy.edisonandroidexercise.network.FactService
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class FactViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var factService: FactService
    private lateinit var observer: Observer<String>
    private lateinit var viewModel: FactViewModel

    @Before
    fun setup() {
        factService = mockk()
        observer = mockk(relaxed = true)
        viewModel = FactViewModel(factService)
        viewModel.fact.observeForever(observer)
    }

    @Test
    fun `updateFact should post new fact when factService returns fact`() = runBlockingTest {
        val expectedFact = "Test Fact"
        coEvery { factService.getFact() } returns FactResponse(fact = expectedFact, length = 100)

        viewModel.updateFact()

        coVerify { observer.onChanged(expectedFact) }
    }

    @Test
    fun `updateFact should post error message when factService throws exception`() = runBlockingTest {
        val exceptionMessage = "Network error"
        coEvery { factService.getFact() } throws RuntimeException(exceptionMessage)

        viewModel.updateFact()

        coVerify { observer.onChanged("something went wrong. error = $exceptionMessage") }
    }

    @Test
    fun `init should post Preview Fact when factService is null`() {
        val defaultViewModel = FactViewModel()
        defaultViewModel.fact.observeForever(observer)

        verify { observer.onChanged("Preview Fact") }
    }
}
