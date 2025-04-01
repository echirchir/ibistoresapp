package dev.chirchir.feature.products.viewmodels

import dev.chirchir.core.ui.base.BaseViewModel
import dev.chirchir.core.ui.base.UiState
import dev.chirchir.domain.products.model.Product
import dev.chirchir.domain.products.usecase.UpdateProductUseCase
import dev.chirchir.feature.products.screen.EditEvent
import dev.chirchir.feature.products.screen.EditProductState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class EditProductViewModel(
    private val updateProductUseCase: UpdateProductUseCase
):BaseViewModel<UiState<Any>, EditEvent>() {

    private val _state: MutableStateFlow<EditProductState> by lazy { MutableStateFlow(EditProductState()) }
    val state = _state.asStateFlow()

    override fun handleEvent(event: EditEvent) {
        when(event) {
            is EditEvent.OnNameChange -> {
                _state.value = _state.value.copy(
                    title = event.name
                )
            }
            is EditEvent.OnPriceChange -> {
                _state.value = _state.value.copy(
                    price = event.price.toDouble()
                )
            }
            is EditEvent.SetProduct -> {
                _state.value = _state.value.copy(
                    id = event.product.id,
                    title = event.product.title,
                    description = event.product.description,
                    brand = event.product.brand,
                    price = event.product.price
                )
            }
            is EditEvent.OnUpdate -> {
                updateProduct(onSuccess = event.onSuccess, onFailure = event.onFailure)
            }
        }
    }

    private fun updateProduct(onSuccess: () -> Unit, onFailure: () -> Unit) = safeLaunch {
        val toUpdate = Product(
            id = state.value.id,
            title = state.value.title,
            description = state.value.description,
            brand = state.value.brand,
            price = state.value.price
        )

        updateProductUseCase.execute(toUpdate)
            .fold(
                onSuccess = { onSuccess() },
                onFailure = { onFailure() }
            )
    }
}