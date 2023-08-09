package com.yunuscagliyan.core_ui.components.anim

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.yunuscagliyan.core.util.Constant.DurationUtil.DEFAULT_ANIMATION_DURATION
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AnimationBox(
    modifier: Modifier = Modifier,
    enter: EnterTransition = expandVertically() + fadeIn(),
    exit: ExitTransition = shrinkVertically() + fadeOut(),
    duration: Long = DEFAULT_ANIMATION_DURATION,
    content: @Composable () -> Unit
) {
    val state = remember{
        MutableTransitionState(false)
    }
    val coroutine = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        coroutine.launch {
            delay(duration)
            state.targetState = true
        }
    }

    AnimatedVisibility(
        visibleState = state,
        enter = enter,
        exit = exit
    ) {
        content()
    }
}