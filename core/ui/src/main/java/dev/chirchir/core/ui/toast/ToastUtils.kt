package dev.chirchir.core.ui.toast

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object MyToast {

    @Composable
    fun SweetSuccess(
        message: String,
        duration: Int = Toast.LENGTH_LONG,
        padding: PaddingValues = PaddingValues(vertical = 28.dp),
        contentAlignment: Alignment = Alignment.TopCenter
    ) {
        val sweetSuccessToast = IBIToast(LocalContext.current)
        sweetSuccessToast.MakeText(
            message = message,
            duration = duration,
            type = Success(),
            padding = padding,
            contentAlignment = contentAlignment
        )
        sweetSuccessToast.show()
    }

    @Composable
    fun SweetError(
        message: String,
        duration: Int = Toast.LENGTH_LONG,
        padding: PaddingValues = PaddingValues(vertical = 28.dp),
        contentAlignment: Alignment = Alignment.TopCenter
    ) {
        val sweetErrorToast = IBIToast(LocalContext.current)
        sweetErrorToast.MakeText(
            message = message,
            duration = duration,
            type = Error(),
            padding = padding,
            contentAlignment = contentAlignment
        )
        sweetErrorToast.show()
    }

    @Composable
    fun SweetInfo(
        message: String,
        duration: Int = Toast.LENGTH_LONG,
        padding: PaddingValues = PaddingValues(vertical = 28.dp),
        contentAlignment: Alignment = Alignment.TopCenter
    ) {
        val sweetInfoToast = IBIToast(LocalContext.current)
        sweetInfoToast.MakeText(
            message = message,
            duration = duration,
            type = Info(),
            padding = padding,
            contentAlignment = contentAlignment
        )
        sweetInfoToast.show()
    }

    @Composable
    fun SweetWarning(
        message: String,
        duration: Int = Toast.LENGTH_LONG,
        padding: PaddingValues = PaddingValues(vertical = 28.dp),
        contentAlignment: Alignment = Alignment.TopCenter
    ) {
        val sweetWarningToast = IBIToast(LocalContext.current)
        sweetWarningToast.MakeText(
            message = message,
            duration = duration,
            type = Warning(),
            padding = padding,
            contentAlignment = contentAlignment
        )
        sweetWarningToast.show()
    }

    @Composable
    internal fun SetView(
        messageTxt: String,
        imageVector: ImageVector,
        contentColor: Color,
        bgColor: Color,
        padding: PaddingValues,
        contentAlignment: Alignment
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .statusBarsPadding(),
            contentAlignment = contentAlignment
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(color = bgColor, shape = RoundedCornerShape(12.dp))
                    .border(width = 1.dp, color = contentColor, shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                color = Color.Transparent
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = imageVector,
                        contentDescription = null,
                        tint = contentColor
                    )
                    Text(
                        text = messageTxt,
                        fontSize = 14.sp,
                        color = contentColor,
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight.Medium,
                    )
                }
            }
        }
    }
}