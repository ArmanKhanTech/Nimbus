package com.android.nimbus.ui.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AutoScrollingText(
    text: String,
    style: TextStyle,
    color: Color,
    modifier: Modifier = Modifier,
    durationMillis: Int = 2500
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = text, block = {
        while (true) {
            coroutineScope.launch {
                scrollState.animateScrollTo(scrollState.maxValue, tween(durationMillis))
            }
            delay(durationMillis.toLong())
            coroutineScope.launch {
                scrollState.animateScrollTo(0, tween(durationMillis))
            }
            delay(durationMillis.toLong())
        }
    })

    Text(
        text = text,
        style = style,
        color = color,
        modifier = modifier
            .horizontalScroll(scrollState)
    )
}