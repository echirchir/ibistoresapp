package dev.chirchir.core.ui.toast

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner

internal class IBIToast(context: Context) : Toast(context) {
    @Composable
    fun MakeText(
        message: String,
        duration: Int = LENGTH_LONG,
        type: ToastProperty,
        padding: PaddingValues,
        contentAlignment: Alignment
    ) {
        val context = LocalContext.current

        val views = ComposeView(context)
        views.setContent {
            MyToast.SetView(
                messageTxt = message,
                imageVector = type.getIcon(),
                contentColor = type.getContentColor(),
                bgColor = type.getBgColor(),
                padding = padding,
                contentAlignment = contentAlignment,
            )
        }
        views.setViewTreeLifecycleOwner(LocalLifecycleOwner.current)
        views.setViewTreeViewModelStoreOwner(LocalViewModelStoreOwner.current)
        views.setViewTreeSavedStateRegistryOwner(LocalSavedStateRegistryOwner.current)
        this.duration = duration
        this.view = views
    }
}