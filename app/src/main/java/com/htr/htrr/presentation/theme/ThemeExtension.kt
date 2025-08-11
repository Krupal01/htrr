package com.htr.htrr.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object ThemeExtension {
    val primary: Color
        @Composable get() = MaterialTheme.colorScheme.primary

    val secondary: Color
        @Composable get() = MaterialTheme.colorScheme.secondary

    val background: Color
        @Composable get() = MaterialTheme.colorScheme.background

    val textPrimary: Color
        @Composable get() = MaterialTheme.colorScheme.onBackground

    val textSecondary: Color
        @Composable get() = MaterialTheme.colorScheme.onSurfaceVariant


}