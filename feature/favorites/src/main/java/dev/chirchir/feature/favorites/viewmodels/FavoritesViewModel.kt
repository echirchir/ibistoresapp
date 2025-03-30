package dev.chirchir.feature.favorites.viewmodels

import android.app.Application
import dev.chirchir.core.ui.base.BaseViewModel
import dev.chirchir.core.ui.base.UiEvent
import dev.chirchir.core.ui.base.UiState

internal class FavoritesViewModel(
    private val application: Application
): BaseViewModel<UiState<Any>, UiEvent>() {

    init {

    }
}