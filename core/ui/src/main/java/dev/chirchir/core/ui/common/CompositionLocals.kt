package dev.chirchir.core.ui.common

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import dev.chirchir.core.ui.common.extension.FixedInsets
import kotlinx.coroutines.CoroutineScope

val LocalFixedInsets = compositionLocalOf<FixedInsets> {
    error("no FixedInsets provided!")
}

val LocalCoroutineScope = staticCompositionLocalOf<CoroutineScope> {
    error("No coroutine scope provided!")
}