package com.tavoeh.secondfeature.presentation.components

import android.app.Activity
import android.os.LocaleList
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.tavoeh.secondfeature.presentation.LandingActivity
import org.junit.Rule
import org.junit.Test
import java.util.Locale

class CounterTest {

//    @get:Rule
//    val composeTestRule = createComposeRule()

    @get:Rule
    val composeTestRule = createAndroidComposeRule<LandingActivity>()

    @Test
    fun testCounter_displaysCorrectValue() {

        setLocale(composeTestRule.activity, Locale("en"))

        composeTestRule.setContent {
            Counter(count = 10)
        }

        composeTestRule.onNodeWithTag("Counter Value").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Counter Value").assert(hasText("10"))
        Thread.sleep(5000)
    }

    @Test
    fun testCounter_increment() {
        setLocale(composeTestRule.activity, Locale("es"))

        val count = mutableStateOf(0)
        composeTestRule.setContent {
            Counter(
                count = count.value,
                increment = { count.value++ }
            )
        }

        composeTestRule.onNodeWithContentDescription("Increment").performClick()
        composeTestRule.onNodeWithTag("Counter Value").assert(hasText("1"))
        Thread.sleep(5000)
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

fun setLocale(context: Activity, locale: Locale) {
    Locale.setDefault(locale)
    val resources = context.resources
    val config = resources.configuration
    config.setLocales(LocaleList(locale))
    resources.updateConfiguration(config, resources.displayMetrics)
//  context.applicationContext.createConfigurationContext(config)
}
