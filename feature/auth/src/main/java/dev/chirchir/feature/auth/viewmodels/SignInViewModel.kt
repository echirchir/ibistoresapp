package dev.chirchir.feature.auth.viewmodels

import android.app.Application
import dev.chirchir.core.ui.base.BaseViewModel
import dev.chirchir.core.ui.base.UiEvent
import dev.chirchir.core.ui.base.UiState

internal class SignInViewModel(
    private val application: Application
): BaseViewModel<UiState<Any>, UiEvent>() {

}