package jp.speakbuddy.edisonandroidexercise.fact

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.coEvery
import io.mockk.mockk
import jp.speakbuddy.edisonandroidexercise.corenetwork.FactService
import jp.speakbuddy.edisonandroidexercise.corenetwork.di.DataStoreRepository
import jp.speakbuddy.edisonandroidexercise.coreui.CoreTestTag
import jp.speakbuddy.edisonandroidexercise.coreui.theme.EdisonAndroidExerciseTheme
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FactScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val factService: FactService = mockk()
    private val dataStoreRepository: DataStoreRepository = mockk()

    @Test
    fun testLoadingState() {
        val expectedFact = "Stored Fact"
        coEvery { dataStoreRepository.lastFact } returns flowOf(expectedFact)

        composeTestRule.setContent {
            EdisonAndroidExerciseTheme {
                FactScreen(
                    viewModel = FakeLoadingFactViewModel(
                        factService,
                        dataStoreRepository
                    )
                )
            }
        }

        composeTestRule.onNodeWithTag(FactTestTag.FACT_BODY).assertDoesNotExist()
        composeTestRule.onNodeWithTag(CoreTestTag.ERROR_CONTENT).assertDoesNotExist()
        composeTestRule.onNodeWithTag(CoreTestTag.LOTTIE_ANIMATION).assertIsDisplayed()
    }

    @Test
    fun testSuccessState() {
        val expectedFact = "Stored Fact"
        coEvery { dataStoreRepository.lastFact } returns flowOf(expectedFact)

        composeTestRule.setContent {
            EdisonAndroidExerciseTheme {
                FactScreen(
                    viewModel = FakeSuccessFactViewModel(
                        factService,
                        dataStoreRepository
                    )
                )
            }
        }

        composeTestRule.onNodeWithTag(FactTestTag.FACT_BODY).assertIsDisplayed()
        composeTestRule.onNodeWithTag(FactTestTag.UPDATE_FACT_BUTTON).assertIsDisplayed()
    }

    @Test
    fun testErrorState() {
        val expectedFact = "Stored Fact"
        coEvery { dataStoreRepository.lastFact } returns flowOf(expectedFact)

        composeTestRule.setContent {
            EdisonAndroidExerciseTheme {
                FactScreen(
                    viewModel = FakeErrorFactViewModel(
                        factService,
                        dataStoreRepository
                    )
                )
            }
        }

        composeTestRule.onNodeWithTag(CoreTestTag.ERROR_CONTENT).assertIsDisplayed()
        composeTestRule.onNodeWithTag(CoreTestTag.TRY_AGAIN_BUTTON).assertIsDisplayed()
    }
}