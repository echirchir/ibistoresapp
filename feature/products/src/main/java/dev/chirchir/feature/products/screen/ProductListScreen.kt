package dev.chirchir.feature.products.screen

import androidx.compose.runtime.Composable
import dev.chirchir.core.ui.components.HeaderView
import dev.chirchir.core.ui.components.ScreenLayout
import dev.chirchir.feature.products.R

@Composable
internal fun ProductListScreen(
    onProductSelected: (String) -> Unit
) {
    ScreenLayout(
        header = {
            HeaderView(title = R.string.products_title)
        }
    ) {

    }
}