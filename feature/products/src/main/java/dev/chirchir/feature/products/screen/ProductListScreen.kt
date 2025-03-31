package dev.chirchir.feature.products.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
                        },
                        onFilterClick = {}
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
    onSearch: (String) -> Unit,
    onFilterClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SearchTextField(
            modifier = Modifier.weight(1f),
            value = state.searchText,
            onValueChanged = { onSearch(it) }
        )

        Spacer(modifier = Modifier.width(12.dp))

        IconButton(
            onClick = onFilterClick,
            modifier = Modifier
                .size(36.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = Icons.Default.FilterList,
                contentDescription = "Filter",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }

    Spacer(modifier = Modifier.height(12.dp))

    LazyColumn(
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        items(products) { product ->
            ProductItem(
                product = product,
                isFavorite = Random.nextBoolean(),
                onClick = {  }
            )
        }
    }
}