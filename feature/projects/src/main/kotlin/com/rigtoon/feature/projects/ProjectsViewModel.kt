package com.rigtoon.feature.projects

import com.rigtoon.core.ui.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/** مدیریت وضعیت صفحه و جلوگیری از درخواست ناوبری تکراری. */
@HiltViewModel
class ProjectsViewModel @Inject constructor() :
    MviViewModel<ProjectsUiState, ProjectsAction, ProjectsEffect>(ProjectsUiState()) {
    override suspend fun handleAction(action: ProjectsAction): Unit {
        when (action) {
            ProjectsAction.ScreenStarted -> {
                updateState { current ->
                    current.copy(isAboutNavigationPending = false)
                }
            }

            ProjectsAction.OpenAbout -> {
                if (!state.value.isAboutNavigationPending) {
                    updateState { current ->
                        current.copy(isAboutNavigationPending = true)
                    }
                    emitEffect(ProjectsEffect.NavigateToAbout)
                }
            }
        }
    }
}
