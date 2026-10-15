package com.rigtoon.app

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rigtoon.app.navigation.AppScreenTags
import com.rigtoon.feature.projects.ProjectsScreenTags
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/** بررسی اجرای واقعی پوسته و مسیر ناوبری. */
@RunWith(AndroidJUnit4::class)
class NavigationTest {
    @get:Rule
    val composeRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity> =
        createAndroidComposeRule()

    @Test
    fun aboutCanBeOpenedAgainAfterReturning(): Unit {
        composeRule.onNodeWithTag(ProjectsScreenTags.ABOUT_BUTTON)
            .performClick()
        composeRule.onNodeWithTag(AppScreenTags.ABOUT_SCREEN)
            .assertIsDisplayed()
        composeRule.onNodeWithTag(AppScreenTags.BACK_BUTTON)
            .performClick()
        composeRule.onNodeWithTag(ProjectsScreenTags.EMPTY_STATE)
            .assertIsDisplayed()
        composeRule.onNodeWithTag(ProjectsScreenTags.ABOUT_BUTTON)
            .performClick()
        composeRule.onNodeWithTag(AppScreenTags.ABOUT_SCREEN)
            .assertIsDisplayed()
    }
}
