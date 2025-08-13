package com.htr.htrr.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = ElectricBlue,
    onPrimary = PureWhite,
    primaryContainer = VeryLightBlue,
    onPrimaryContainer = VeryLightBlue,

    secondary = CyberPurple,
    onSecondary = PureWhite,
    secondaryContainer = VeryLightPurple,
    onSecondaryContainer = DeepPurple,

    tertiary = NeonGreen,
    onTertiary = PureWhite,
    tertiaryContainer = VeryLightGreen,
    onTertiaryContainer = ForestGreen,

    background = SoftWhite,
    onBackground = CharcoalBlack,

    surface = PureWhite,
    onSurface = CharcoalBlack,
    surfaceVariant = LightGray,
    onSurfaceVariant = DarkGray,

    error = ErrorRed,
    onError = PureWhite,
    errorContainer = VeryLightRed,
    onErrorContainer = DarkRed,

    outline = MediumGray,
    outlineVariant = LightOutlineVariant,

    surfaceTint = ElectricBlue,
    inverseSurface = CharcoalBlack,
    inverseOnSurface = SoftWhite,
    inversePrimary = DarkPrimary
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,
    primaryContainer = DarkPrimaryContainer,
    onPrimaryContainer = DarkOnPrimaryContainer,

    secondary = DarkSecondary,
    onSecondary = DarkOnSecondary,
    secondaryContainer = DarkSecondaryContainer,
    onSecondaryContainer = DarkOnSecondaryContainer,

    tertiary = DarkTertiary,
    onTertiary = DarkOnTertiary,
    tertiaryContainer = DarkTertiaryContainer,
    onTertiaryContainer = DarkOnTertiaryContainer,

    background = DarkBackground,
    onBackground = DarkOnBackground,

    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurfaceVariant,

    error = DarkError,
    onError = DarkOnError,
    errorContainer = DarkErrorContainer,
    onErrorContainer = DarkOnErrorContainer,

    outline = DarkOutline,
    outlineVariant = DarkOutlineVariant,

    surfaceTint = DarkPrimary,
    inverseSurface = SoftWhite,
    inverseOnSurface = CharcoalBlack,
    inversePrimary = ElectricBlue
)

@Composable
fun HtrrTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}