package jp.speakbuddy.edisonandroidexercise.search

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import jp.speakbuddy.edisonandroidexercise.model.FactListResponse
import jp.speakbuddy.edisonandroidexercise.model.FactResponse
import jp.speakbuddy.edisonandroidexercise.network.FactService
import jp.speakbuddy.edisonandroidexercise.ui.search.SearchScreen
import jp.speakbuddy.edisonandroidexercise.ui.search.SearchViewModel
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val factService: FactService = mockk()

    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        viewModel = SearchViewModel(factService)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun searchScreen_displaysIdleState() {
        composeTestRule.setContent {
            SearchScreen(viewModel = viewModel)
        }

        composeTestRule.onNodeWithText(
            "Enter a query to search for cat facts."
        ).assertExists()
    }

    @Test
    fun searchScreen_displaysSuccessState() = runTest {
        val facts = listOf(
            FactResponse("Cat fact 1", 10),
            FactResponse("Another cat fact 2", 20)
        )
        coEvery { factService.getFacts(any(), any()) } returns FactListResponse(facts)

        composeTestRule.setContent {
            SearchScreen(viewModel = viewModel)
        }

        composeTestRule.runOnIdle {
            viewModel.searchFacts("cat")
        }

        composeTestRule.onNodeWithText("Cat fact 1").assertExists()
        composeTestRule.onNodeWithText("Another cat fact 2").assertExists()
    }

    @Test
    fun searchScreen_displaysNoDataState() = runTest {
        val facts = listOf<FactResponse>()
        coEvery { factService.getFacts(any(), any()) } returns FactListResponse(facts)

        composeTestRule.setContent {
            SearchScreen(viewModel = viewModel)
        }

        composeTestRule.runOnIdle {
            viewModel.searchFacts("cat")
        }

        composeTestRule.onNodeWithText(
            "No facts found for query: cat"
        ).assertExists()
    }

    @Test
    fun searchScreen_displaysErrorState() = runTest {
        val errorMessage = "Service Error"
        coEvery { factService.getFacts(any(), any()) } throws RuntimeException(errorMessage)

        composeTestRule.setContent {
            SearchScreen(viewModel = viewModel)
        }

        composeTestRule.runOnIdle {
            viewModel.searchFacts("cat")
        }

        composeTestRule.onNodeWithText(errorMessage).assertExists()
    }

    @Test
    fun searchScreen_hidesKeyboardOnSearch() {
        composeTestRule.setContent {
            SearchScreen(viewModel = viewModel)
        }

        composeTestRule.onNodeWithText(
            "Search for cat facts"
        ).performTextInput("cat")
        composeTestRule.onNodeWithText(
            "Search"
        ).performClick()
    }
}