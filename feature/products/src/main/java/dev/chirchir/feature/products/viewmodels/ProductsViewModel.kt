package dev.chirchir.feature.products.viewmodels

import android.app.Application
import dev.chirchir.core.ui.base.BaseViewModel
import dev.chirchir.domain.products.model.PaginationModel
import dev.chirchir.domain.products.model.Product
import dev.chirchir.domain.products.usecase.GetProductsUseCase
import dev.chirchir.feature.products.screen.FilterOption
import dev.chirchir.feature.products.screen.ProductsState
import dev.chirchir.feature.products.screen.ProductsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class ProductsViewModel(
    private val application: Application,
    private val getProductsUseCase: GetProductsUseCase
): BaseViewModel<ProductsUiState, ProductsState.Event>() {

    private val _productsState = MutableStateFlow<ProductsUiState>(ProductsUiState.Loading)
    val productsState: StateFlow<ProductsUiState> = _productsState.asStateFlow()

    private val _state: MutableStateFlow<ProductsState.State> by lazy { MutableStateFlow(
        ProductsState.State()
    ) }
    val state = _state.asStateFlow()

    private var productsList: List<Product>? = null
    private var limit: Int = 100
    private var totalProducts: Int = 0
    private var skip: Int = 0

    init {
        getPaginatedProducts()
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

    private fun getPaginatedProducts() = safeLaunch {
        updateUiState { ProductsUiState.Loading }
        getProductsUseCase.execute(PaginationModel(limit = limit, skip = skip))
            .fold(
                onSuccess = { results ->
                    updateUiState { ProductsUiState.Success(results.products) }
                    productsList = results.products
                    totalProducts = results.total
                    skip = results.skip
                    limit = results.limit
                    _productsState.value = ProductsUiState.Success(results.products)
                },
                onFailure = { error ->
                    _productsState.value = ProductsUiState.Fail(error.message ?: "Unknown error")
                }
            )
    }

    private fun loadMoreProducts() {

    }

    fun refreshProducts() {

    }
}