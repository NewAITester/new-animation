package com.rigtoon.feature.projects

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.rigtoon.core.ui.components.EmptyState
import com.rigtoon.core.ui.theme.RigToonDimensions

/** صفحهٔ خالی پروژه‌ها با فاصله‌گذاری تطبیقی و پشتیبانی از متن بزرگ. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectsScreen(
    state: ProjectsUiState,
    onAction: (ProjectsAction) -> Unit,
    modifier: Modifier = Modifier,
): Unit {
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(R.string.projects_title))
                },
                actions = {
                    TextButton(
                        onClick = { onAction(ProjectsAction.OpenAbout) },
                        enabled = !state.isAboutNavigationPending,
                        modifier = Modifier.testTag(ProjectsScreenTags.ABOUT_BUTTON),
                    ) {
                        Text(text = stringResource(R.string.projects_about))
                    }
                },
            )
        },
    ) { innerPadding ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            val contentPadding: Dp = if (maxWidth >= RigToonDimensions.expandedWidthBreakpoint) {
                RigToonDimensions.tabletPadding
            } else {
                RigToonDimensions.phonePadding
            }
            val minimumContentHeight: Dp = maxHeight

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .heightIn(min = minimumContentHeight)
                    .padding(contentPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                EmptyState(
                    title = stringResource(R.string.projects_empty_title),
                    description = stringResource(R.string.projects_empty_description),
                    modifier = Modifier.testTag(ProjectsScreenTags.EMPTY_STATE),
                )
            }
        }
    }
}
