package dev.chirchir.feature.products.screen

import dev.chirchir.core.ui.base.UiEvent
import dev.chirchir.domain.products.model.Product

data class ProductDetailsState(
    val product: Product? = null
)

sealed class DetailEvent: UiEvent {
    data class SetProduct(val product: Product): DetailEvent()
    data class DeleteProduct(val onDelete: () -> Unit, val onFail: () ->  Unit): DetailEvent()
    object FavoriteProduct: DetailEvent()
}