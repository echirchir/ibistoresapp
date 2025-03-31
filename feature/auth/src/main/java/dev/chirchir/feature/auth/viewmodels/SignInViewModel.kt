package dev.chirchir.feature.auth.viewmodels

import android.app.Application
import android.content.Context
import dev.chirchir.core.ui.base.BaseViewModel
import dev.chirchir.core.ui.base.UiEvent
import dev.chirchir.core.ui.base.UiState
import dev.chirchir.core.ui.common.extension.isBiometricsAvailableOrEnabled
import dev.chirchir.core.ui.common.extension.showBiometricPrompt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class SignInViewModel(
    private val application: Application
): BaseViewModel<UiState<Any>, UiEvent>() {

    private val _biometricUiState: MutableStateFlow<UiState<Any?>> by lazy { MutableStateFlow(UiState.Idle) }
    val biometricUiState = _biometricUiState.asStateFlow()

    val isBiometricEnabled: Boolean get() {
        return application.isBiometricsAvailableOrEnabled()
    }

    fun showBiometricPrompt(context: Context) {
        if (isBiometricEnabled) {
            context.showBiometricPrompt(
                onSuccess = ::signIn,
                onError = {
                    _biometricUiState.update { UiState.Failure("Authentication failed. Try again!") }
                }
            )
        }
    }

    fun signIn() {
        _biometricUiState.update { UiState.Success(null) }
    }
}