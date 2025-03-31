package dev.chirchir.feature.products.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.chirchir.core.ui.components.CenteredLoading
import dev.chirchir.core.ui.components.FailedView
import dev.chirchir.core.ui.components.HeaderView
import dev.chirchir.core.ui.components.SearchTextField
import dev.chirchir.domain.products.model.Product
import dev.chirchir.feature.products.R
import dev.chirchir.feature.products.viewmodels.ProductsViewModel
import org.koin.androidx.compose.getViewModel
import kotlin.random.Random

@Composable
internal fun ProductListScreen(
    viewModel: ProductsViewModel = getViewModel(),
    onProductSelected: (String) -> Unit
) {
    val productsState by viewModel.uiState.collectAsState()
    val state by viewModel.state.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            HeaderView(
                title = R.string.products_title,
                color = MaterialTheme.colorScheme.background
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
        ) {
            when(val products = productsState) {
                is ProductsUiState.Fail -> {
                    FailedView(onRetry = { viewModel.refreshProducts() })
                }

                ProductsUiState.Loading -> {
                    CenteredLoading(modifier = Modifier.fillMaxSize())
                }
                is ProductsUiState.Success -> {
                    Success(
                        products = products.data,
                        state = state,
                        onSearch = { query ->
                            viewModel.handleEvent(ProductsState.Event.SearchTextChange(query))
                        }
                    )
                }
                ProductsUiState.Empty -> Unit
                else -> Unit
            }
        }
    }
}

@Composable
private fun Success(
    products: List<Product>,
    state: ProductsState.State,
    onSearch: (String) -> Unit
) {
    SearchTextField(
        modifier = Modifier.padding(horizontal = 24.dp),
        value = state.searchText,
        onValueChanged = {
            onSearch(it)
        }
    )

    Spacer(modifier = Modifier.height(4.dp))

    LazyColumn(
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        items(products) { product ->
            ProductItem(product, isFavorite = Random.nextBoolean()) {

            }
        }
    }
}