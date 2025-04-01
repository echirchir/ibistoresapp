package dev.chirchir.feature.favorites.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.chirchir.core.ui.base.UiState
import dev.chirchir.core.ui.components.EmptyView
import dev.chirchir.core.ui.components.HeaderView
import dev.chirchir.core.ui.components.ProductItem
import dev.chirchir.domain.products.model.Product
import dev.chirchir.feature.favorites.R
import dev.chirchir.feature.favorites.viewmodels.FavoritesViewModel
import org.koin.androidx.compose.getViewModel

@Composable
internal fun FavoritesScreen(
    viewModel: FavoritesViewModel = getViewModel(),
    onSelect: (Product) -> Unit
) {
    val favoritesUiState by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            HeaderView(
                title = R.string.favorites_title,
                color = MaterialTheme.colorScheme.background
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(top = it.calculateTopPadding())
        ) {
            when(val products = favoritesUiState) {
                UiState.Loading -> {

                }
                is UiState.Success -> {
                    if(products.data.isEmpty()) EmptyView()
                    else SuccessView(products.data, onSelect = { onSelect(it)})
                }
                else -> Unit
            }
        }
    }
}

@Composable
private fun SuccessView(
    products: List<Product>,
    onSelect: (Product) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        items(products.size) { index ->
            ProductItem(
                product = products[index],
                onClick = { onSelect(products[index]) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}