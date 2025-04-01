package dev.chirchir.feature.products.screen

import dev.chirchir.core.ui.base.UiEvent
import dev.chirchir.domain.products.model.Product

data class EditProductState(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val brand: String? = "",
    val price: Double = 0.0,
    val favorited: Boolean = false
) {
    internal fun canUpdate() = title.isNotBlank() && price > 0.0
}

sealed class EditEvent: UiEvent {
    data class SetProduct(val product: Product) : EditEvent()
    data class OnNameChange(val name: String): EditEvent()
    data class OnPriceChange(val price: String): EditEvent()
    data class OnUpdate(val onSuccess: () -> Unit, val onFailure: () -> Unit): EditEvent()
}