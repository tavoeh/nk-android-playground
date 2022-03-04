package com.tavoeh.secondfeature.presentation.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
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
    val viewModel = mockk<LandingViewModel>()

    @Test
    fun shapesScreen() {
        every { viewModel.uiState } returns MutableStateFlow(UIState.Error(Exception()))
        composeTestRule.setContent {
            ShapesScreen(viewModel = viewModel)
        }
        composeTestRule.onNodeWithText("Error").assertIsDisplayed()
    }
}