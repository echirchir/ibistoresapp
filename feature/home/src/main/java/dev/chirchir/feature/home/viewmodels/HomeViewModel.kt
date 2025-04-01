package dev.chirchir.feature.home.viewmodels

import android.app.Application
import dev.chirchir.core.ui.base.BaseViewModel
import dev.chirchir.core.ui.base.UiEvent
import dev.chirchir.core.ui.base.UiState
import dev.chirchir.domain.products.usecase.GetProductByIdUseCase
import dev.chirchir.domain.products.usecase.GetProductsUseCase

internal class HomeViewModel(
    private val application: Application,
    private val getProductsUseCase: GetProductsUseCase,
    private val getProductById: GetProductByIdUseCase
) : BaseViewModel<UiState<Any>, UiEvent>() {

}