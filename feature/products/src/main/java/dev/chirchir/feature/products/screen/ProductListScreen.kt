package dev.chirchir.feature.products.screen

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import dev.chirchir.core.ui.components.HeaderView
import dev.chirchir.core.ui.components.ScreenLayout
import dev.chirchir.feature.products.R
import dev.chirchir.feature.products.viewmodels.ProductsViewModel
import org.koin.androidx.compose.getViewModel

@Composable
internal fun ProductListScreen(
    viewModel: ProductsViewModel = getViewModel(),
    onProductSelected: (String) -> Unit
) {
    ScreenLayout(
        color = MaterialTheme.colorScheme.background,
        header = {
            HeaderView(
                title = R.string.products_title,
                color = MaterialTheme.colorScheme.background
            )
        }
    ) {

    }
}