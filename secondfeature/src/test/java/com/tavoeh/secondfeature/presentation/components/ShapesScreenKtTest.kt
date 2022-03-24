package com.tavoeh.secondfeature.presentation.components

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.tavoeh.secondfeature.presentation.LandingViewModel
import com.tavoeh.secondfeature.presentation.LandingViewModel.UIState.Data
import com.tavoeh.secondfeature.presentation.LandingViewModel.UIState.Error
import com.tavoeh.secondfeature.presentation.LandingViewModel.UIState.Loading
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(instrumentedPackages = ["androidx.loader.content"])
class ShapesScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private val viewModel = mockk<LandingViewModel>()

    @Test
    fun `Given UI Error, when ShapesScreen is initialized, then display error message`() {
        every { viewModel.uiState } returns MutableStateFlow(Error(Exception()))
        composeTestRule.setContent {
            ShapesScreen(viewModel = viewModel)
        }
        composeTestRule.onNodeWithText("Error").assertIsDisplayed()
    }

    @Test
    fun `Given UI is Loading, when ShapesScreen is initialized, then display loading message`() {
        every { viewModel.uiState } returns MutableStateFlow(Loading)
        composeTestRule.setContent {
            ShapesScreen(viewModel = viewModel)
        }
        composeTestRule.onNodeWithText("Loading").assertIsDisplayed()
    }

    @Test
    fun `Given valid UI data, when ShapesScreen is initialized, then display valid data`() {
        every { viewModel.uiState } returns MutableStateFlow(Data(listOf("Hello", "World")))
        composeTestRule.setContent {
            ShapesScreen(viewModel = viewModel)
        }
        composeTestRule.onNodeWithTag("data").onChildren().assertCountEquals(2)
    }
}
