package com.android.nimbus.utility

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut

enum class ScaleTransitionDirection {
    INWARDS,
    OUTWARDS
}

fun scaleInTransition(
    direction: ScaleTransitionDirection = ScaleTransitionDirection.INWARDS,
    initialScale: Float = if (direction == ScaleTransitionDirection.OUTWARDS) 0.8f else 1.2f
): EnterTransition {
    return scaleIn(
        animationSpec = tween(220, 90),
        initialScale = initialScale
    ) + fadeIn(tween(220, 90))
}

fun scaleOutTransition(
    direction: ScaleTransitionDirection = ScaleTransitionDirection.OUTWARDS,
    targetScale: Float = if (direction == ScaleTransitionDirection.INWARDS) 0.8f else 1.2f
): ExitTransition {
    return scaleOut(
        animationSpec = tween(220, 90),
        targetScale = targetScale
    ) + fadeOut(tween(220, 90))
}