package com.android.nimbus.ui.components

import android.annotation.SuppressLint
import android.util.DisplayMetrics
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun TopToast(
    modifier: Modifier = Modifier,
    messageType: MessageType = MessageType.SUCCESS,
    message: String = "An unexpected error occurred. Please try again later",
    height: Dp = 100.dp,
    width: Dp? = null,
    onDismissCallback: @Composable () -> Unit = {},
) {
    var hasTransitionStarted by remember { mutableStateOf(false) }
    var slideDownAnimation by remember { mutableStateOf(true) }
    var animationStarted by remember { mutableStateOf(false) }
    var showMessage by remember { mutableStateOf(false) }
    var dismissCallback by remember { mutableStateOf(false) }

    val displayMetrics: DisplayMetrics = LocalContext.current.resources.displayMetrics
    val screenWidthInDp = width ?: (displayMetrics.widthPixels / displayMetrics.density).dp

    val boxWidth by animateDpAsState(
        targetValue = if (hasTransitionStarted) screenWidthInDp else 0.dp,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
        label = "Box Width",
    )

    val boxHeight by animateDpAsState(
        targetValue = if (hasTransitionStarted) height else 0.dp,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
        label = "Box Height",
    )

    if (!animationStarted) {
        LaunchedEffect(Unit) {
            slideDownAnimation = false

            hasTransitionStarted = true
            showMessage = true

            delay(2000)
            hasTransitionStarted = false
            showMessage = false

            slideDownAnimation = true
            animationStarted = true
            dismissCallback = true
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {
        Box(
            modifier = modifier
                .size(boxWidth, boxHeight)
                .clip(RoundedCornerShape(16.dp))
                .background(getColorForMessageType(messageType))
                .align(alignment = Alignment.TopCenter),
            contentAlignment = Alignment.Center,
        ) {
            if (showMessage) {
                Text(
                    text = message,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(16.dp),
                )
            }

            if (dismissCallback) onDismissCallback()
        }
    }
}

enum class MessageType {
    SUCCESS,
    ERROR
}

@SuppressLint("NewApi")
@Composable
fun getColorForMessageType(messageType: MessageType): Color {
    return when (messageType) {
        MessageType.SUCCESS -> MaterialTheme.colorScheme.primary
        MessageType.ERROR -> Color.Red
    }
}