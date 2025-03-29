package dev.chirchir.core.ui.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.chirchir.core.ui.theme.primary100

@Composable
fun CircularProgress(
    modifier: Modifier = Modifier,
    color: Color = primary100,
) {
    CircularProgressIndicator(
        modifier = modifier,
        strokeWidth = 2.dp,
        color = color,
    )
}