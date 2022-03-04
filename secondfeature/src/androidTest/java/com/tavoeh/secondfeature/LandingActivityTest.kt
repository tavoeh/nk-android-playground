package com.tavoeh.secondfeature

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.tavoeh.secondfeature.presentation.App
import com.tavoeh.secondfeature.presentation.ui.theme.MyPlaygroundTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LandingActivityTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp(){
        composeTestRule.setContent {
            MyPlaygroundTheme {
                App()
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