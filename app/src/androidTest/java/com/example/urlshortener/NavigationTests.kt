package com.example.urlshortener

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.urlshortener.URlApp
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController

    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            URlApp()
        }
    }

    @Test
    fun verifyHomePage() {
        composeTestRule
            .onNodeWithText("Enter URL")
            .assertIsDisplayed()
    }

    @Test
    fun navigateToHistory() {
        composeTestRule
            .onNodeWithContentDescription("Check History")
            .performClick()
        composeTestRule
            .onNodeWithText("History")
            .assertIsDisplayed()
    }


    @Test
    fun canShortenURL() {
        composeTestRule
            .onNodeWithText("Enter URL")
            .performTextInput("https://clouro.be")
        composeTestRule
            .onNodeWithText("Shorten URL")
            .performClick()
        composeTestRule
            .onNodeWithText("Loading...")
            .assertIsDisplayed()
        // Wait until the results screen is displayed
        Thread.sleep(5000)

        composeTestRule
            .onNodeWithContentDescription("Go Back Button")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithContentDescription("Copy")
            .assertIsDisplayed()
    }
}
