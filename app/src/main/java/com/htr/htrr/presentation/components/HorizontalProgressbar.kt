package com.htr.htrr.presentation.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun HorizontalProgressbar(
    progress: Float,
    modifier: Modifier = Modifier,
    height: Dp = 8.dp,
    width: Dp = 200.dp,
    backgroundColor: Color = colorScheme.onSecondary,
    progressColor: Color = colorScheme.secondary,
    cornerRadius: Dp = 4.dp,
    animationDuration: Int = 1000,
    animationDelay: Int = 0
) {
    // Animate progress value from 0 to target progress
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 100f), // Allow 0-100 percentage
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animationDelay,
            easing = FastOutSlowInEasing
        ),
        label = "progress_animation"
    )

    // Convert percentage to fraction for width calculation
    val progressFraction = animatedProgress / 100f

    Box(
        modifier = modifier
            .width(width)
            .height(height)
            .clip(RoundedCornerShape(cornerRadius))
            .background(backgroundColor)
    ) {
        // Progress box with calculated width
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(width * progressFraction) // Calculate actual width based on percentage
                .clip(RoundedCornerShape(cornerRadius))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            progressColor,
                            progressColor.copy(alpha = 0.8f)
                        )
                    )
                )
        )
    }
}