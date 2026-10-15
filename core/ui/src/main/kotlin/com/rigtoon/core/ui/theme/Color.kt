package com.rigtoon.core.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

internal val RigToonDarkColors: ColorScheme = darkColorScheme(
    primary = Color(0xFF79E0D4),
    onPrimary = Color(0xFF003731),
    primaryContainer = Color(0xFF005047),
    onPrimaryContainer = Color(0xFF98F7EA),
    secondary = Color(0xFFBACBC6),
    onSecondary = Color(0xFF253430),
    background = Color(0xFF111418),
    onBackground = Color(0xFFE2E2E8),
    surface = Color(0xFF111418),
    onSurface = Color(0xFFE2E2E8),
    surfaceVariant = Color(0xFF404946),
    onSurfaceVariant = Color(0xFFBFC9C4),
    outline = Color(0xFF89938E),
)

internal val RigToonLightColors: ColorScheme = lightColorScheme(
    primary = Color(0xFF006B60),
    onPrimary = Color.White,
    primaryContainer = Color(0xFF98F7EA),
    onPrimaryContainer = Color(0xFF00201C),
    secondary = Color(0xFF4D635B),
    onSecondary = Color.White,
    background = Color(0xFFF7FAF8),
    onBackground = Color(0xFF191C1B),
    surface = Color(0xFFF7FAF8),
    onSurface = Color(0xFF191C1B),
    surfaceVariant = Color(0xFFDBE5DF),
    onSurfaceVariant = Color(0xFF404946),
    outline = Color(0xFF707973),
)
