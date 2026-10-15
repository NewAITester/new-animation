package com.rigtoon.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

/** تم مشترک برنامه؛ حالت تاریک پیش‌فرض است. */
@Composable
fun RigToonTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit,
): Unit {
    MaterialTheme(
        colorScheme = if (darkTheme) RigToonDarkColors else RigToonLightColors,
        typography = RigToonTypography,
        content = content,
    )
}
