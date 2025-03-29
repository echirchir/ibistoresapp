package dev.chirchir.core.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import dev.chirchir.core.ui.theme.white

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScreenLayout (
    color: Color = white,
    header: (@Composable () -> Unit)? = null,
    footer: (@Composable () -> Unit)? = null,
    content: @Composable LazyItemScope.(PaddingValues) -> Unit
) {
    val focusManager = LocalFocusManager.current
    Scaffold(
        modifier = Modifier
            .navigationBarsPadding()
            .background(color)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { focusManager.clearFocus() },
            ),
        topBar = {
            header?.invoke()
        },
        bottomBar = {
            footer?.invoke()
        },
        containerColor = color
    ) {
        LazyColumn {
            item {
                content(it)
            }
        }
    }
}