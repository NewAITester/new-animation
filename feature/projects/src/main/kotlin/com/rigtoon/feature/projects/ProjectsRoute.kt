package com.rigtoon.feature.projects

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle

/** اتصال وضعیت و اثرها به چرخهٔ عمر مقصد ناوبری. */
@Composable
fun ProjectsRoute(
    viewModel: ProjectsViewModel,
    onOpenAbout: () -> Unit,
): Unit {
    val state: ProjectsUiState by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.onAction(ProjectsAction.ScreenStarted)
            viewModel.effects.collect { effect ->
                when (effect) {
                    ProjectsEffect.NavigateToAbout -> onOpenAbout()
                }
            }
        }
    }

    ProjectsScreen(
        state = state,
        onAction = viewModel::onAction,
    )
}
