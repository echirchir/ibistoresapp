package dev.chirchir.core.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chirchir.core.ui.theme.primary100
import dev.chirchir.core.ui.theme.white

@Composable
fun MyButton(
    modifier: Modifier = Modifier,
    @StringRes text: Int,
    enabled: Boolean = true,
    loading: Boolean = false,
    color: Color = primary100,
    radius: Dp = 16.dp,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier.fillMaxWidth().height(54.dp),
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(radius),
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            disabledContainerColor = color.copy(alpha = 0.2f),
        ),
    ) {
        if (loading) CircularProgress(modifier = Modifier.size(24.dp), color = white)
        else {
            Text(
                text = stringResource(id = text),
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = white,
                maxLines = 1,
            )
        }
    }
}