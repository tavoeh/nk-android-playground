package com.tavoeh.secondfeature.presentation.components

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class CounterTest {

    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun testCounter_displaysCorrectValue() {
        composeTestRule.setContent {
            Counter(count = 10)
        }

        composeTestRule.onNodeWithTag("Counter Value").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Counter Value").assert(hasText("10"))
    }

    @Test
    fun testCounter_increment() {
        val count = mutableStateOf(0)
        composeTestRule.setContent {
            Counter(
                count = count.value,
                increment = { count.value++ }
            )
        }

        composeTestRule.onNodeWithContentDescription("Increment").performClick()
        composeTestRule.onNodeWithTag("Counter Value").assert(hasText("1"))
    }

    @Test
    fun testCounter_decrement() {
        val count = mutableStateOf(10)

        composeTestRule.setContent {
            Counter(
                count = count.value,
                decrement = { count.value-- }
            )
        }

        composeTestRule.onNodeWithContentDescription("Decrement").performClick()
        composeTestRule.onNodeWithTag("Counter Value").assert(hasText("9"))
    }
}
