package dev.chirchir.core.ui.base

sealed class UiState<out T> {
    object Idle : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Failure(val message: String) : UiState<Nothing>()
}

val <T> UiState<T>.successData: T? get() = (this as? UiState.Success)?.data