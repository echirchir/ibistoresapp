package dev.chirchir.feature.favorites.viewmodels

import dev.chirchir.core.ui.base.BaseViewModel
import dev.chirchir.core.ui.base.UiEvent
import dev.chirchir.core.ui.base.UiState
import dev.chirchir.domain.products.model.Product
import dev.chirchir.domain.products.usecase.GetFavoritesUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

internal class FavoritesViewModel(
    private val getFavorites: GetFavoritesUseCase
): BaseViewModel<UiState<List<Product>>, UiEvent>() {

    init {
        loadFavorites()
    }

    private fun loadFavorites() = safeLaunch {
        updateUiState { UiState.Loading }
        getFavorites.execute()
            .onEach { result ->
                result.fold(
                    onSuccess = { results ->
                        if (results.products.isEmpty()) {
                            updateUiState { UiState.Success(emptyList()) }
                        } else {
                            updateUiState { UiState.Success(results.products) }
                        }
                    },
                    onFailure = { error ->
                        updateUiState { UiState.Failure(error.message ?: "Unknown error") }
                    }
                )
            }
            .launchIn(this)
    }
}