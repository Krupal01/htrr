package com.htr.htrr.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.htr.htrr.presentation.theme.colorScheme

@Composable
fun CustomTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textColor: Color = colorScheme.primary,
    disabledTextColor: Color = colorScheme.onSurface.copy(alpha = 0.38f),
    fontSize: TextUnit = 14.sp,
    fontWeight: FontWeight = FontWeight.Medium,
    contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
    minWidth: Dp = Dp.Unspecified,
    minHeight: Dp = 36.dp,
    shape: Shape = RoundedCornerShape(4.dp),
    rippleColor: Color = colorScheme.primary.copy(alpha = 0.12f),
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    TextButton(
        onClick = onClick,
        modifier = modifier
            .defaultMinSize(minWidth = minWidth, minHeight = minHeight),
        enabled = enabled,
        shape = shape,
        contentPadding = contentPadding,
        colors = ButtonDefaults.textButtonColors(
            contentColor = textColor,
            disabledContentColor = disabledTextColor
        ),
        interactionSource = remember { MutableInteractionSource() }
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingIcon?.invoke()
            Text(
                text = text,
                fontSize = fontSize,
                fontWeight = fontWeight
            )
            trailingIcon?.invoke()
        }
    }
}

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    backgroundColor: Color = colorScheme.primary,
    disabledBackgroundColor: Color = colorScheme.onSurface.copy(alpha = 0.12f),
    contentColor: Color = colorScheme.onPrimary,
    disabledContentColor: Color = colorScheme.onSurface.copy(alpha = 0.38f),
    fontSize: TextUnit = 14.sp,
    fontWeight: FontWeight = FontWeight.Medium,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
    minWidth: Dp = 64.dp,
    minHeight: Dp = 40.dp,
    shape: Shape = RoundedCornerShape(8.dp),
    elevation: ButtonElevation = ButtonDefaults.buttonElevation(
        defaultElevation = 2.dp,
        pressedElevation = 8.dp,
        disabledElevation = 0.dp
    ),
    border: BorderStroke? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .defaultMinSize(minWidth = minWidth, minHeight = minHeight),
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor,
            disabledContainerColor = disabledBackgroundColor,
            disabledContentColor = disabledContentColor
        ),
        elevation = elevation,
        border = border,
        contentPadding = contentPadding
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingIcon?.invoke()
            Text(
                text = text,
                fontSize = fontSize,
                fontWeight = fontWeight
            )
            trailingIcon?.invoke()
        }
    }
}

@Composable
fun CustomOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    backgroundColor: Color = Color.Transparent,
    disabledBackgroundColor: Color = Color.Transparent,
    contentColor: Color = colorScheme.primary,
    disabledContentColor: Color = colorScheme.onSurface.copy(alpha = 0.38f),
    borderColor: Color = colorScheme.outline,
    disabledBorderColor: Color = colorScheme.onSurface.copy(alpha = 0.12f),
    borderWidth: Dp = 1.dp,
    fontSize: TextUnit = 14.sp,
    fontWeight: FontWeight = FontWeight.Medium,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
    minWidth: Dp = 64.dp,
    minHeight: Dp = 40.dp,
    shape: Shape = RoundedCornerShape(8.dp),
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .defaultMinSize(minWidth = minWidth, minHeight = minHeight),
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = backgroundColor,
            contentColor = contentColor,
            disabledContainerColor = disabledBackgroundColor,
            disabledContentColor = disabledContentColor
        ),
        border = BorderStroke(
            width = borderWidth,
            color = if (enabled) borderColor else disabledBorderColor
        ),
        contentPadding = contentPadding
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingIcon?.invoke()
            Text(
                text = text,
                fontSize = fontSize,
                fontWeight = fontWeight
            )
            trailingIcon?.invoke()
        }
    }
}
