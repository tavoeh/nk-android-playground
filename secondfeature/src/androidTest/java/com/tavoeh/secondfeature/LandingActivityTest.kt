package com.tavoeh.secondfeature

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.tavoeh.secondfeature.presentation.App
import com.tavoeh.secondfeature.presentation.LandingViewModel
import com.tavoeh.secondfeature.presentation.ui.theme.MyPlaygroundTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LandingActivityTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            MyPlaygroundTheme {
                App(LandingViewModel())
            }
        }
    }

    @Test
    fun testButtonClick() {
        composeTestRule.onNodeWithTag("Counter Value").assertIsDisplayed()
    }

    @Test
    fun `GivenLandingScreenWhenCountIsClickedThenIncreaseCount`() {
        composeTestRule.onNodeWithTag("Counter Value").assert(hasText("0"))
        composeTestRule.onNodeWithContentDescription("Increment").performClick()
        composeTestRule.onNodeWithTag("Counter Value").assert(hasText("1"))
    }
}
