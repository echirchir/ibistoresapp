package dev.chirchir.feature.home.viewmodels

import android.app.Application
import dev.chirchir.core.ui.base.BaseViewModel
import dev.chirchir.core.ui.base.UiEvent
import dev.chirchir.core.ui.base.UiState

internal class HomeViewModel(
    private val application: Application
) : BaseViewModel<UiState<Any>, UiEvent>() {

}