package dev.chirchir.feature.products.viewmodels

import android.app.Application
import dev.chirchir.core.ui.base.BaseViewModel
import dev.chirchir.domain.products.model.Product
import dev.chirchir.domain.products.usecase.GetProductsUseCase
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

    init {
        getPaginatedProducts()
    }

    override fun handleEvent(event: ProductsState.Event) {
        when(event) {
            is ProductsState.Event.SearchTextChange -> {
                _state.update { _state.value.copy(searchText = event.text) }
                filter(event.text)
            }
            is ProductsState.Event.FilterProducts -> {

            }
        }
    }

    private fun filter(text: String) = productsList?.filter { it.toString().uppercase().contains(text.uppercase()) }?.let { data ->
        if (data.isEmpty()) updateUiState { ProductsUiState.Empty }
        else updateUiState { ProductsUiState.Success(data) }
    }

    private fun getPaginatedProducts() = safeLaunch {
        updateUiState { ProductsUiState.Loading }
        getProductsUseCase.execute()
            .fold(
                onSuccess = { results ->
                    updateUiState { ProductsUiState.Success(results.products) }
                    productsList = results.products
                    _productsState.value = ProductsUiState.Success(results.products)
                },
                onFailure = { error ->
                    _productsState.value = ProductsUiState.Fail(error.message ?: "Unknown error")
                }
            )
    }

    fun searchQuery(query: String) {

    }

    fun refreshProducts() {
        getPaginatedProducts()
    }
}