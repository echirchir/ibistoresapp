package dev.chirchir.feature.products.viewmodels

import dev.chirchir.core.ui.base.BaseViewModel
import dev.chirchir.core.ui.base.UiState
import dev.chirchir.domain.products.model.PaginationModel
import dev.chirchir.domain.products.model.Product
import dev.chirchir.domain.products.usecase.GetProductsUseCase
import dev.chirchir.feature.products.screen.FilterOption
import dev.chirchir.feature.products.screen.ProductsState
import dev.chirchir.feature.products.screen.ProductsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

internal class ProductsViewModel(
    private val getProductsUseCase: GetProductsUseCase
): BaseViewModel<ProductsUiState, ProductsState.Event>() {

    private val _state: MutableStateFlow<ProductsState.State> by lazy { MutableStateFlow(
        ProductsState.State()
    ) }
    val state = _state.asStateFlow()

    private val _scrollState: MutableStateFlow<Any?> by lazy { MutableStateFlow(UiState.Idle) }
    val scrollState = _scrollState.asStateFlow()

    private var productsList: List<Product>? = null
    private var totalProducts = 0
    private var limit = 50
    private var skip = 0

    init {
        loadProducts()
    }

    override fun handleEvent(event: ProductsState.Event) {
        when(event) {
            is ProductsState.Event.SearchTextChange -> {
                _state.update { _state.value.copy(searchText = event.text) }
                applySearch(event.text)
            }
            is ProductsState.Event.FilterProducts -> {
                _state.update { _state.value.copy(filterOption = event.option) }
                applyFilter(event.option)
            }
            ProductsState.Event.LoadMore -> {
                loadMoreProducts()
            }
        }
    }

    private fun applyFilter(option: FilterOption) {
        val currentList = productsList?: emptyList()
        val filteredResults = when (option) {
            FilterOption.PRICE_ASCENDING -> currentList.sortedBy { it.price }
            FilterOption.PRICE_DESCENDING -> currentList.sortedByDescending { it.price }
            FilterOption.NAME_ASCENDING -> currentList.sortedBy { it.title.lowercase() }
            FilterOption.NAME_DESCENDING -> currentList.sortedByDescending { it.title.lowercase() }
            FilterOption.NONE -> currentList
        }
        updateUiState { ProductsUiState.Success(filteredResults) }
    }

    private fun applySearch(text: String) = productsList?.filter { it.toString().uppercase().contains(text.uppercase()) }?.let { data ->
        if (data.isEmpty()) updateUiState { ProductsUiState.Empty }
        else updateUiState { ProductsUiState.Success(data) }
    }

    private fun loadProducts() = safeLaunch {
        updateUiState { ProductsUiState.Loading }
        getProductsUseCase.execute(PaginationModel(limit = limit, skip = skip))
            .onEach { result ->
                result.fold(
                    onSuccess = { results ->
                        if (results.products.isEmpty()) {
                            updateUiState { ProductsUiState.Empty }
                        } else {
                            updateUiState { ProductsUiState.Success(results.products) }
                            productsList = results.products
                            totalProducts = results.total
                            skip = results.products.size
                        }
                    },
                    onFailure = { error ->
                        updateUiState { ProductsUiState.Fail(error.message ?: "Unknown error") }
                    }
                )
            }
            .launchIn(this)
    }

    private fun loadMoreProducts() = safeLaunch {
        val hasMoreProducts = totalProducts > (productsList?.size ?: 0)
        if (hasMoreProducts) {
            _scrollState.value = UiState.Loading

            getProductsUseCase.execute(
                PaginationModel(
                    limit = limit,
                    skip = productsList?.size ?: 0
                )
            ).onEach { result ->
                result.fold(
                    onSuccess = { results ->
                        _scrollState.value = UiState.Idle
                        skip += results.products.size

                        productsList = (productsList.orEmpty() + results.products)
                            .distinctBy { it.id }

                        updateUiState { ProductsUiState.Success(productsList ?: emptyList()) }
                    },
                    onFailure = {
                        _scrollState.value = UiState.Idle
                    }
                )
            }.launchIn(this)
        }
    }

    fun refreshProducts() = loadProducts()
}