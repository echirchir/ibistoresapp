package dev.chirchir.feature.products.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.chirchir.core.ui.base.UiState
import dev.chirchir.core.ui.components.CenteredLoading
import dev.chirchir.core.ui.components.CircularProgress
import dev.chirchir.core.ui.components.EmptyView
import dev.chirchir.core.ui.components.FailedView
import dev.chirchir.core.ui.components.HeaderView
import dev.chirchir.core.ui.components.ProductItem
import dev.chirchir.core.ui.components.SearchTextField
import dev.chirchir.domain.products.model.Product
import dev.chirchir.feature.products.R
import dev.chirchir.feature.products.viewmodels.ProductsViewModel
import org.koin.androidx.compose.getViewModel

@Composable
internal fun ProductListScreen(
    viewModel: ProductsViewModel = getViewModel(),
    onProductSelected: (Product) -> Unit
) {
    val productsState by viewModel.uiState.collectAsState()

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
                is ProductsUiState.Empty -> {
                    EmptyView()
                }

                is ProductsUiState.Fail -> {
                    FailedView(onRetry = { viewModel.refreshProducts() })
                }

                ProductsUiState.Loading -> {
                    CenteredLoading(modifier = Modifier.fillMaxSize())
                }
                is ProductsUiState.Success -> {
                    Success(
                        products = products.data,
                        viewModel = viewModel,
                        onSearch = { query ->
                            viewModel.handleEvent(ProductsState.Event.SearchTextChange(query))
                        },
                        onApplyFilter = {
                            viewModel.handleEvent(ProductsState.Event.FilterProducts(it))
                        },
                        onSelect = { product ->
                            onProductSelected(product)
                        }
                    )
                }
                ProductsUiState.Empty -> EmptyView()
                else -> Unit
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Success(
    products: List<Product>,
    viewModel: ProductsViewModel,
    onSearch: (String) -> Unit,
    onApplyFilter: (filterOption: FilterOption) -> Unit,
    onSelect: (Product) -> Unit
) {
    var showFilterDialog by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val state by viewModel.state.collectAsState()
    val scrollUiState by viewModel.scrollState.collectAsState()

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
            onClick = {
                showFilterDialog = true
            },
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
        items(products.size) { index ->
            if(index >= products.size - 1 && viewModel.state.value.canReload()) {
                LaunchedEffect(index) {
                    viewModel.handleEvent(ProductsState.Event.LoadMore)
                }
            }
            ProductItem(
                product = products[index],
                onClick = { onSelect(products[index]) }
            )
        }
        when (scrollUiState) {
            UiState.Loading -> {
                item {
                    LoadMoreView()
                }
            }
            else -> Unit
        }

        item {
            Spacer(modifier = Modifier.height(60.dp))
        }
    }

    if(showFilterDialog) {
        ModalBottomSheet(
            dragHandle = null,
            scrimColor = Color.Black.copy(alpha = 0.4f),
            onDismissRequest = {
                showFilterDialog = false
            }, sheetState = sheetState
        ) {
            ProductFilterSheet(
                state.filterOption,
                onApplyFilter = {
                    showFilterDialog = false
                    onApplyFilter(it)
                }
            )
        }
    }
}

@Composable
private fun LoadMoreView() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgress(modifier = Modifier.size(18.dp))
    }
}