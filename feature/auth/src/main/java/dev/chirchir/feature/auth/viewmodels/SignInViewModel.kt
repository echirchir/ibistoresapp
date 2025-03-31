package dev.chirchir.feature.auth.viewmodels

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.chirchir.core.ui.base.BaseViewModel
import dev.chirchir.core.ui.base.UiState
import dev.chirchir.core.ui.common.extension.isBiometricsAvailableOrEnabled
import dev.chirchir.core.ui.common.extension.showBiometricPrompt
import dev.chirchir.feature.auth.screen.SignInState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class SignInViewModel(
    private val application: Application
): BaseViewModel<UiState<Any>, SignInState.Event>() {

    private val _biometricUiState: MutableStateFlow<UiState<Any?>> by lazy { MutableStateFlow(UiState.Idle) }
    val biometricUiState = _biometricUiState.asStateFlow()

    private val _authUiState: MutableStateFlow<UiState<Any?>> by lazy { MutableStateFlow(UiState.Idle) }
    val authUiState = _authUiState.asStateFlow()

    var state by mutableStateOf(SignInState.State())

    val isBiometricEnabled: Boolean get() {
        return application.isBiometricsAvailableOrEnabled()
    }

    override fun handleEvent(event: SignInState.Event) {

        when(event) {
            is SignInState.Event.OnEmailChange -> {
                isEmailValid(event.email)
            }
            is SignInState.Event.OnPasswordChange -> {
                isPasswordValid(event.password)
            }
            is SignInState.Event.Submit -> {
                authenticateUser()
            }
        }
    }

    private fun isEmailValid(email: String = state.email) {
        state = if (email.isEmpty()) {
            state.copy(emailError = "Enter a valid email address", email = email)
        } else {
            state.copy(emailError = null, email = email)
        }
    }

    private fun isPasswordValid(password: String = state.password) {
        state = if (password.isEmpty()) {
            state.copy(passwordError = "Enter a valid password", password = password)
        } else {
            state.copy(passwordError = null, password = password)
        }
    }

    private fun authenticateUser() {
        _authUiState.update { UiState.Success(null) }
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