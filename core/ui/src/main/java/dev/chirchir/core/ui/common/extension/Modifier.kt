package dev.chirchir.core.ui.common.extension

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.shimmerEffect(isLoading: Boolean): Modifier {
    return if (isLoading) {
        this.then(
            Modifier.background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.surface,
                        MaterialTheme.colorScheme.surfaceVariant,
                        MaterialTheme.colorScheme.surface
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(1000f, 0f)
                ),
                shape = RoundedCornerShape(4.dp)
            )
        )
    } else {
        this
    }
}