package dev.chirchir.feature.auth.screen

import dev.chirchir.core.ui.base.UiEvent
import dev.chirchir.core.ui.common.extension.isValidEmail

internal class SignInState {
    sealed class Event: UiEvent {
        data class OnEmailChange(val email: String): Event()
        data class OnPasswordChange(val password: String): Event()
        object Submit: Event()
    }

    data class State(
        val email: String = "",
        val emailError: String? = null,
        val password: String = "",
        val passwordError: String? = null,
        val canContinue: Boolean = false
    )
}

internal fun SignInState.State.canContinue() = email.isValidEmail() && password.isNotBlank() && emailError == null && passwordError == null