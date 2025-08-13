package com.htr.htrr.presentation.theme

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.htr.htrr.presentation.theme.typography


// STEP 1: Helper functions to work with RGB values
fun Color.toRgb(): Triple<Int, Int, Int> {
    return Triple(
        (red * 255).toInt(),
        (green * 255).toInt(),
        (blue * 255).toInt()
    )
}

fun Color.toRgbString(): String {
    val (r, g, b) = toRgb()
    return "RGB($r, $g, $b)"
}

fun rgbColor(r: Int, g: Int, b: Int): Color {
    return Color(
        red = r / 255f,
        green = g / 255f,
        blue = b / 255f
    )
}

// STEP 2: Debug composable with RGB values
@Composable
fun RGBColorDebugger() {
    val colors = MaterialTheme.colorScheme

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                "=== RGB COLOR VALUES ===",
                style = MaterialTheme.typography.headlineSmall
            )
        }

        // Show all important colors with RGB values
        val colorList = listOf(
            "Primary" to colors.primary,
            "OnPrimary" to colors.onPrimary,
            "Secondary" to colors.secondary,
            "OnSecondary" to colors.onSecondary,
            "SecondaryContainer" to colors.secondaryContainer,
            "OnSecondaryContainer" to colors.onSecondaryContainer,
            "Background" to colors.background,
            "Surface" to colors.surface,
            "SurfaceVariant" to colors.surfaceVariant,
            "Outline" to colors.outline
        )

        items(colorList) { (name, color) ->
            val (r, g, b) = color.toRgb()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Color preview box
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(color, RoundedCornerShape(4.dp))
                        .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                )

                // Color info
                Column {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "RGB($r, $g, $b)",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        item {
            Text(
                "=== TEST PROGRESS BAR ===",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        item {
            TestProgressBarWithRGB()
        }

        item {
            Text(
                "=== CUSTOM RGB COLORS TEST ===",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        item {
            CustomRGBColorsTest()
        }
    }
}

@Composable
fun TestProgressBarWithRGB() {
    val colors = MaterialTheme.colorScheme
    val bgColor = colors.secondaryContainer
    val progressColor = colors.onSecondaryContainer

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Background Color: ${bgColor.toRgbString()}")
        Text("Progress Color: ${progressColor.toRgbString()}")

        // Progress bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
                .background(bgColor, RoundedCornerShape(12.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.75f)
                    .background(progressColor, RoundedCornerShape(12.dp))
            )
        }

        // Expected vs Actual comparison
        Text("Expected RGB values (if using custom theme):")
        Text("• Background should be your secondaryContainer RGB")
        Text("• Progress should be your onSecondaryContainer RGB")
    }
}

@Composable
fun CustomRGBColorsTest() {
    // Define some test colors with RGB values
    val testBgColor = rgbColor(230, 225, 229) // Light purple-ish
    val testProgressColor = rgbColor(103, 80, 164) // Purple

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Test with known RGB values:")
        Text("Background: ${testBgColor.toRgbString()}")
        Text("Progress: ${testProgressColor.toRgbString()}")

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
                .background(testBgColor, RoundedCornerShape(12.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.75f)
                    .background(testProgressColor, RoundedCornerShape(12.dp))
            )
        }
    }
}

// STEP 3: Theme with RGB values
@Composable
fun RGBTestTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = lightColorScheme(
        primary = rgbColor(103, 80, 164), // RGB(103, 80, 164)
        onPrimary = rgbColor(255, 255, 255), // RGB(255, 255, 255)
        secondary = rgbColor(98, 91, 113), // RGB(98, 91, 113)
        onSecondary = rgbColor(255, 255, 255), // RGB(255, 255, 255)
        secondaryContainer = rgbColor(232, 222, 248), // RGB(232, 222, 248)
        onSecondaryContainer = rgbColor(29, 25, 43), // RGB(29, 25, 43)
        background = rgbColor(255, 251, 254), // RGB(255, 251, 254)
        onBackground = rgbColor(28, 27, 31), // RGB(28, 27, 31)
        surface = rgbColor(255, 251, 254), // RGB(255, 251, 254)
        onSurface = rgbColor(28, 27, 31) // RGB(28, 27, 31)
    )

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}

// STEP 4: Complete test with RGB validation
@Composable
fun CompleteRGBTest() {
    HtrrTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            RGBColorDebugger()
        }
    }
}

// STEP 5: Your progress bar with RGB validation
@Composable
fun ValidatedProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    height: Dp = 8.dp,
    width: Dp = 200.dp
) {
    val colors = MaterialTheme.colorScheme
    val backgroundColor = colors.secondaryContainer
    val progressColor = colors.onSecondaryContainer

    // Validate colors
    val (bgR, bgG, bgB) = backgroundColor.toRgb()
    val (progR, progG, progB) = progressColor.toRgb()

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            "Progress: ${progress.toInt()}%",
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            "BG: RGB($bgR, $bgG, $bgB)",
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            "Progress: RGB($progR, $progG, $progB)",
            style = MaterialTheme.typography.bodySmall
        )

        val animatedProgress by animateFloatAsState(
            targetValue = progress.coerceIn(0f, 100f),
            animationSpec = tween(1000),
            label = "progress"
        )

        val progressFraction = animatedProgress / 100f

        Box(
            modifier = modifier
                .width(width)
                .height(height)
                .clip(RoundedCornerShape(4.dp))
                .background(backgroundColor)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(width * progressFraction)
                    .clip(RoundedCornerShape(4.dp))
                    .background(progressColor)
            )
        }
    }
}

// STEP 6: Usage in MainActivity
/*
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompleteRGBTest() // This will show RGB values
        }
    }
}
*/