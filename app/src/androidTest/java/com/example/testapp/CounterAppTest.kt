package com.example.testapp

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CounterAppTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testInitialState() {
        composeTestRule.onNodeWithContentDescription("CounterText").assertTextEquals("0")
    }

    @Test
    fun testIncrementAction() {
        composeTestRule.onNodeWithText("Increment").performClick()
        composeTestRule.onNodeWithContentDescription("CounterText").assertTextEquals("1")
    }

    @Test
    fun testDecrementAction() {
        composeTestRule.onNodeWithText("Decrement").performClick()
        composeTestRule.onNodeWithContentDescription("CounterText").assertTextEquals("-1")
    }
}