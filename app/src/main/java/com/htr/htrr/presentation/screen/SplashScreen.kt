package com.htr.htrr.presentation.screen

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRowScopeInstance.align
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.delay

@Composable
fun SplashScreen (
    onSplashFinished: () -> Unit,
){
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        alpha.animateTo(1f, animationSpec = tween(1000))
        delay(2000) // Keep splash for 2 seconds
        onSplashFinished()
    }

    Column(
        modifier = Modifier.fillMaxSize().
    ) {

    }
}