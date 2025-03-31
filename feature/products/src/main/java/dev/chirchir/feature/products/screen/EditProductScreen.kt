package dev.chirchir.feature.products.screen

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import dev.chirchir.core.ui.components.HeaderView
import dev.chirchir.core.ui.components.ScreenLayout
import dev.chirchir.domain.products.model.Product
import dev.chirchir.feature.products.R

@Composable
internal fun EditProductScreen(
    product: Product,
    onBack: () -> Unit
) {
    ScreenLayout(
        color = MaterialTheme.colorScheme.background,
        header = {
            HeaderView(
                title = R.string.edit_product_title,
                onBack = onBack
            )
        }
    ) {

    }
}