package jp.speakbuddy.edisonandroidexercise.history

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import jp.speakbuddy.edisonandroidexercise.ui.history.FactHistoryCard
import jp.speakbuddy.edisonandroidexercise.ui.history.FactHistoryScreen
import jp.speakbuddy.edisonandroidexercise.ui.history.FactHistoryTestTags
import jp.speakbuddy.edisonandroidexercise.ui.history.FactHistoryViewModel
import jp.speakbuddy.edisonandroidexercise.ui.theme.EdisonAndroidExerciseTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FactHistoryScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun factHistoryScreen_displaysNoFactsFound_whenHistoryIsEmpty() {
        composeTestRule.setContent {
            EdisonAndroidExerciseTheme {
                FactHistoryScreen(viewModel = FakeEmptyFactHistoryViewModel())
            }
        }

        composeTestRule.onNodeWithTag(FactHistoryTestTags.NO_FACTS_FOUND).assertExists()
    }

    @Test
    fun factHistoryScreen_displaysFactHistory_whenHistoryIsNotEmpty() {
        composeTestRule.setContent {
            EdisonAndroidExerciseTheme {
                FactHistoryScreen(viewModel = FakeFactHistoryViewModel())
            }
        }

        composeTestRule.onNodeWithTag(FactHistoryTestTags.FACT_HISTORY_LIST).assertExists()
        composeTestRule.onNodeWithText("Stored Fact 1").assertExists()
        composeTestRule.onNodeWithText("Stored Fact 2").assertExists()
    }

    @Test
    fun factHistoryCard_displaysFactDetails_correctly() {
        val fact = "This is a very long fact with multiple cats. " +
                "This fact is specifically designed to be long enough " +
                "to trigger the length condition and contains the word cats multiple times."
        composeTestRule.setContent {
            EdisonAndroidExerciseTheme {
                FactHistoryCard(fact = fact)
            }
        }

        composeTestRule.onNodeWithTag(FactHistoryTestTags.FACT_TEXT).assertExists()
        composeTestRule.onNodeWithTag(FactHistoryTestTags.FACT_LENGTH).assertExists()
        composeTestRule.onNodeWithTag(FactHistoryTestTags.MULTIPLE_CATS).assertExists()
    }
}

class FakeEmptyFactHistoryViewModel : FactHistoryViewModel(null) {
    init {
        // Initialize with empty history
        setFactHistory(emptyList())
    }
}

class FakeFactHistoryViewModel : FactHistoryViewModel(null) {
    init {
        // Initialize with some facts
        setFactHistory(listOf("Stored Fact 1", "Stored Fact 2"))
    }
}