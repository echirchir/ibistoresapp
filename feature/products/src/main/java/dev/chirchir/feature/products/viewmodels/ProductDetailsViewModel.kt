package dev.chirchir.feature.products.viewmodels

import android.util.Log
import dev.chirchir.core.ui.base.BaseViewModel
import dev.chirchir.core.ui.base.UiState
import dev.chirchir.domain.products.usecase.DeleteProductUseCase
import dev.chirchir.domain.products.usecase.GetProductByIdUseCase
import dev.chirchir.domain.products.usecase.ToggleFavoriteUseCase
import dev.chirchir.feature.products.screen.DetailEvent
import dev.chirchir.feature.products.screen.ProductDetailsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductDetailsViewModel(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val favoriteUseCase: ToggleFavoriteUseCase,
    private val deleteProductUseCase: DeleteProductUseCase
): BaseViewModel<UiState<Any>, DetailEvent>() {

    private val _state: MutableStateFlow<ProductDetailsState> by lazy { MutableStateFlow(
        ProductDetailsState()
    ) }
    val state = _state.asStateFlow()

    override fun handleEvent(event: DetailEvent) {
        when(event) {
            is DetailEvent.SetProduct -> {
                _state.value = _state.value.copy(product = event.product)
            }

            is DetailEvent.DeleteProduct -> {
                deleteProduct(onDelete = event.onDelete, onFail = event.onFail)
            }
            DetailEvent.FavoriteProduct -> {
                _state.value.product?.let { currentProduct ->
                    val newProduct = currentProduct.copy(
                        isFavorited = !currentProduct.isFavorited
                    )
                    _state.value = _state.value.copy(product = newProduct)
                    favoriteProduct()
                }
            }
        }
    }

    fun reloadProduct() = safeLaunch {
        state.value.product?.id?.let {
            getProductByIdUseCase.execute(it)
                .fold(
                    onSuccess = { product ->
                        _state.value = _state.value.copy(product = product)
                    },
                    onFailure = {
                        Log.d("FAILED", "Something really bad happened here!")
                    }
                )
        }
    }

    private fun favoriteProduct() = safeLaunch {
        state.value.product?.id?.let {
            favoriteUseCase.execute(it).fold(
                onSuccess = {},
                onFailure = {}
            )
        }
    }

    private fun deleteProduct(onDelete: () -> Unit, onFail: () -> Unit) = safeLaunch {
        state.value.product?.id?.let {
            deleteProductUseCase.execute(it).fold(
                onSuccess = { onDelete() },
                onFailure = { onFail() }
            )
        }
    }
}