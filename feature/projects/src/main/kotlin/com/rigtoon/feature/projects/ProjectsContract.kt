package com.rigtoon.feature.projects

import androidx.compose.runtime.Immutable

/** وضعیت تغییرناپذیر صفحهٔ پروژه‌ها در فاز ساختار اولیه. */
@Immutable
data class ProjectsUiState(
    val isAboutNavigationPending: Boolean = false,
)

/** ورودی‌های صفحهٔ پروژه‌ها. */
sealed interface ProjectsAction {
    data object ScreenStarted : ProjectsAction

    data object OpenAbout : ProjectsAction
}

/** اثرهای تک‌مصرفی صفحهٔ پروژه‌ها. */
sealed interface ProjectsEffect {
    data object NavigateToAbout : ProjectsEffect
}

/** شناسه‌های پایدار تست؛ این مقادیر متن قابل نمایش نیستند. */
object ProjectsScreenTags {
    const val EMPTY_STATE: String = "projects:empty"
    const val ABOUT_BUTTON: String = "projects:about"
}
