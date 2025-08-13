package com.htr.htrr.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.htr.htrr.presentation.theme.getGradientColors
import com.htr.htrr.presentation.theme.typography
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SplashScreen(
    onNavigateToMain: () -> Unit = {}
) {
    var isVisible by remember { mutableStateOf(false) }
    var showContent by remember { mutableStateOf(false) }

    // Animate visibility on launch
    LaunchedEffect(Unit) {
        showContent = true
        delay(100)
        isVisible = true
    }

    // Auto navigate after delay (optional)
//    LaunchedEffect(Unit) {
//        delay(3000) // 3 seconds
//        onNavigateToMain()
//    }

    val gradientColors = getGradientColors()

    // Animation values
    val titleAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 300,
            easing = FastOutSlowInEasing
        ),
        label = "titleAlpha"
    )

    val subtitleAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 600,
            easing = FastOutSlowInEasing
        ),
        label = "subtitleAlpha"
    )

    val titleScale by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0.8f,
        animationSpec = tween(
            durationMillis = 800,
            delayMillis = 300,
            easing = FastOutSlowInEasing
        ),
        label = "titleScale"
    )

    val subtitleTranslationY by animateFloatAsState(
        targetValue = if (isVisible) 0f else 50f,
        animationSpec = tween(
            durationMillis = 800,
            delayMillis = 600,
            easing = FastOutSlowInEasing
        ),
        label = "subtitleTranslation"
    )

    // Rotating gradient animation
    val infiniteTransition = rememberInfiniteTransition(label = "gradient")
    val gradientAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 8000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "gradientRotation"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.sweepGradient(
                    colors = gradientColors + gradientColors.first(), // Complete the circle
                    center = Offset.Infinite
                ).let { brush ->
                    // Apply rotation to the gradient
                    Brush.linearGradient(
                        colors = gradientColors,
                        start = Offset(
                            x = cos(Math.toRadians(gradientAngle.toDouble())).toFloat() * 1000f,
                            y = sin(Math.toRadians(gradientAngle.toDouble())).toFloat() * 1000f
                        ),
                        end = Offset(
                            x = cos(Math.toRadians(gradientAngle.toDouble() + 180)).toFloat() * 1000f,
                            y = sin(Math.toRadians(gradientAngle.toDouble() + 180)).toFloat() * 1000f
                        )
                    )
                }
            )
            .systemBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        // Animated background elements
        AnimatedBackgroundElements(isVisible = showContent)

        // Main content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        ) {
            // App Icon or Logo placeholder
            /*Box(
                modifier = Modifier
                    .size(120.dp)
                    .scale(titleScale)
                    .alpha(titleAlpha)
                    .background(
                        color = colorScheme.surface.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(24.dp)
                    )
                    .border(
                        width = 2.dp,
                        color = colorScheme.onBackground.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(24.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_dialog_info), // Replace with your app icon
                    contentDescription = "App Logo",
                    modifier = Modifier.size(60.dp),
                    tint = colorScheme.onBackground
                )
            }*/

            Spacer(modifier = Modifier.height(32.dp))
            // App Title
            Text(
                text = "MyAwesome App", // Replace with your app name
                style = typography.headlineLarge.copy(
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-0.5).sp
                ),
                color = colorScheme.onPrimaryContainer,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .alpha(titleAlpha)
                    .scale(titleScale)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // App Subtitle
            Text(
                text = "Innovate • Create • Inspire", // Replace with your app tagline
                style = typography.titleMedium.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.5.sp
                ),
                color = colorScheme.onPrimaryContainer.copy(alpha = 0.8f),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .alpha(subtitleAlpha)
                    .offset(y = subtitleTranslationY.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Loading indicator
            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn(
                    animationSpec = tween(
                        durationMillis = 500,
                        delayMillis = 1200
                    )
                )
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(32.dp),
                    color = colorScheme.onPrimaryContainer.copy(alpha = 0.6f),
                    strokeWidth = 3.dp
                )
            }
        }

        // Version info at bottom
        AnimatedVisibility(
            visible = isVisible,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(
                    durationMillis = 600,
                    delayMillis = 1000
                )
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        ) {
            Text(
                text = "Version 1.0.0", // Replace with actual version
                style = typography.bodySmall,
                color = colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
private fun AnimatedBackgroundElements(isVisible: Boolean) {
    val infiniteTransition = rememberInfiniteTransition(label = "background")

    // Floating particles
    repeat(6) { index ->
        val animationDelay = index * 200
        val animationDuration = 4000 + (index * 500)

        val offsetY by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = -100f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = animationDuration,
                    delayMillis = animationDelay,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            ),
            label = "particle$index"
        )

        val alpha by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 0.6f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = animationDuration / 2,
                    delayMillis = animationDelay
                ),
                repeatMode = RepeatMode.Reverse
            ),
            label = "particleAlpha$index"
        )

        if (isVisible) {
            Box(
                modifier = Modifier
                    .offset(
                        x = ((-200..200).random()).dp + (index * 60).dp,
                        y = offsetY.dp + (600 + index * 100).dp
                    )
                    .size((8..16).random().dp)
                    .alpha(alpha)
                    .background(
                        color = Color.White.copy(alpha = 0.3f),
                        shape = CircleShape
                    )
            )
        }
    }
}