package dev.chirchir.feature.products.screen

import dev.chirchir.core.ui.base.UiEvent
import dev.chirchir.domain.products.model.Product

internal sealed class ProductsUiState {
    object Loading: ProductsUiState()
    object Empty: ProductsUiState()
    data class Success(val data: List<Product>): ProductsUiState()
    data class Fail(val message: String? = null): ProductsUiState()
}

internal class ProductsState {
    sealed class Event: UiEvent {
        data class SearchTextChange(val text: String) : Event()
        data class FilterProducts(val option: FilterOption): Event()
        object LoadMore: Event()
    }

    data class State(
        val searchText: String = "",
        val filterOption: FilterOption = FilterOption.NONE,
        val totalProducts: Int = 0,
        val limit: Int = 50,
        val skip: Int = 0
    )
}