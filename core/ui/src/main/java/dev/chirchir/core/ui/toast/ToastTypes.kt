package dev.chirchir.core.ui.toast

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import dev.chirchir.core.ui.theme.accent100
import dev.chirchir.core.ui.theme.grey05
import dev.chirchir.core.ui.theme.grey100
import dev.chirchir.core.ui.theme.primary05
import dev.chirchir.core.ui.theme.primary70
import dev.chirchir.core.ui.theme.red10
import dev.chirchir.core.ui.theme.red100

internal class Success : ToastProperty {
    override fun getIcon(): ImageVector = Icons.Rounded.CheckCircle
    override fun getContentColor(): Color = primary70
    override fun getBgColor(): Color = primary05
}

internal class Error : ToastProperty {
    override fun getIcon(): ImageVector = Icons.Rounded.Close
    override fun getContentColor(): Color = red100
    override fun getBgColor(): Color = red10
}

internal class Info : ToastProperty {
    override fun getIcon(): ImageVector = Icons.Rounded.Info
    override fun getContentColor(): Color = grey100
    override fun getBgColor(): Color = grey05
}

internal class Warning : ToastProperty {
    override fun getIcon(): ImageVector = Icons.Rounded.Warning
    override fun getContentColor(): Color = accent100
    override fun getBgColor(): Color = accent100.copy(alpha = 0.1f)
}