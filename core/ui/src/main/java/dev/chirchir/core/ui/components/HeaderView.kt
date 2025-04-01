package dev.chirchir.core.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chirchir.core.ui.theme.white

@Composable
fun HeaderView(
    modifier: Modifier = Modifier,
    @StringRes title: Int? = null,
    color: Color = white,
    onClose: (() -> Unit)? = null,
    onBack: (() -> Unit)? = null,
) {
    Column {
        Column(
            modifier = modifier
                .background(color.copy(alpha = 0.96f))
                .statusBarsPadding()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row {
                onBack?.let {
                    ItemView(icon = Icons.AutoMirrored.Default.ArrowBack, onClick = it)
                }
                Spacer(modifier = Modifier.weight(1f))
                onClose?.let {
                    ItemView(icon = Icons.Default.Close, onClick = it)
                }
            }
            title?.let {
                Text(
                    text = stringResource(id = it),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(brush = Brush.verticalGradient(
                    colors = listOf(color.copy(alpha = 0.96f), color.copy(alpha = 0.96f), Color.Transparent))
                )
        )
    }
}

@Composable
private fun ItemView(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(36.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable(
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}