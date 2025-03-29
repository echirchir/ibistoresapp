package dev.chirchir.core.ui.toast

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

internal interface ToastProperty {
    fun getIcon(): ImageVector
    fun getContentColor(): Color
    fun getBgColor(): Color
}