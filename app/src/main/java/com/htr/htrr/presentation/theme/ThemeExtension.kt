package com.htr.htrr.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val colorScheme: ColorScheme
    @Composable get() = MaterialTheme.colorScheme
val typography: Typography
    @Composable get() = MaterialTheme.typography

val ColorScheme.accent: Color
    @Composable get() = if (isSystemInDarkTheme()) AccentGradientStart else AccentGradientEnd

val ColorScheme.highlight: Color
    @Composable get() = HighlightColor

val ColorScheme.success: Color
    @Composable get() = SuccessColor

val ColorScheme.warning: Color
    @Composable get() = WarningColor

// Custom gradient colors
@Composable
fun getGradientColors(): List<Color> {
    return if (isSystemInDarkTheme()) {
        listOf(DarkPrimary, DarkSecondary, DarkTertiary)
    } else {
        listOf(LightPrimary, LightSecondary, LightTertiary)
    }
}