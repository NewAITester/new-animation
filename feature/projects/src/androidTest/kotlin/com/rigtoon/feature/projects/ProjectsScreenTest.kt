package com.rigtoon.feature.projects

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.LayoutDirection
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rigtoon.core.ui.theme.RigToonTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/** بررسی حالت خالی و تعامل در جهت RTL. */
@RunWith(AndroidJUnit4::class)
class ProjectsScreenTest {
    @get:Rule
    val composeRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun emptyStateIsVisible(): Unit {
        composeRule.setContent {
            RigToonTheme {
                ProjectsScreen(
                    state = ProjectsUiState(),
                    onAction = {},
                )
            }
        }
        composeRule.onNodeWithTag(ProjectsScreenTags.EMPTY_STATE)
            .assertIsDisplayed()
    }

    @Test
    fun aboutActionWorksInRtl(): Unit {
        val actions: MutableList<ProjectsAction> = mutableListOf()
        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                RigToonTheme {
                    ProjectsScreen(
                        state = ProjectsUiState(),
                        onAction = { action -> actions.add(action) },
                    )
                }
            }
        }
        composeRule.onNodeWithTag(ProjectsScreenTags.ABOUT_BUTTON)
            .assertIsDisplayed()
            .performClick()
        composeRule.runOnIdle {
            assertEquals(listOf(ProjectsAction.OpenAbout), actions)
        }
    }
}
