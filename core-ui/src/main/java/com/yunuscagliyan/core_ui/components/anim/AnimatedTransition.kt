package com.yunuscagliyan.core_ui.components.anim

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.runtime.Composable

@Composable
fun AnimatedTransition(
    visible: Boolean,
    enter: EnterTransition = expandVertically(),
    exit: ExitTransition = shrinkVertically(),
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = enter,
        exit = exit
    ) {
        content()
    }
}