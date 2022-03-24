package com.tavoeh.secondfeature.presentation.components

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.tavoeh.secondfeature.presentation.LandingViewModel
import com.tavoeh.secondfeature.presentation.LandingViewModel.UIState
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

internal class ShapesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private val viewModel = mockk<LandingViewModel>()

    @Test
    fun shapesScreen() {
        every { viewModel.uiState } returns MutableStateFlow(UIState.Error(Exception()))
        composeTestRule.setContent {
            ShapesScreen(viewModel = viewModel)
        }
        composeTestRule.onNodeWithText("Error").assertIsDisplayed()
    }

    @Test
    fun givenLoadingDisplayLoading() {
        every { viewModel.uiState } returns MutableStateFlow(UIState.Loading)
        composeTestRule.setContent {
            ShapesScreen(viewModel = viewModel)
        }
        composeTestRule.onNodeWithText("Loading").assertIsDisplayed()
    }

    @Test
    fun givenDataDisplayData() {
        every { viewModel.uiState } returns MutableStateFlow(UIState.Data(listOf("Hello", "World")))
        composeTestRule.setContent {
            ShapesScreen(viewModel = viewModel)
        }
        composeTestRule.onNodeWithTag("data").onChildren().assertCountEquals(2)
    }
}
