package dev.chirchir.core.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModel<T, EVENT : UiEvent> : ViewModel() {

    private val _uiState: MutableStateFlow<T?> by lazy {
        MutableStateFlow(null)
    }
    val uiState = _uiState.asStateFlow()

    val eventHandler: (EVENT) -> Unit = ::handleEvent

    protected val handler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception, COROUTINE_EXCEPTION_HANDLER_MESSAGE)
    }

    private var timeoutJob: Job? = null

    open fun handleEvent(event: EVENT) {}

    protected fun updateUiState(updateFunc: (T?) -> T?) {
        _uiState.update(updateFunc)
    }

    protected fun onTimeout(time: Long = (35 * 1000), completion: () -> Unit) {
        timeoutJob = viewModelScope.launch {
            delay(time)
            completion()
        }
    }

    protected fun cancelTimeout() = timeoutJob?.cancel()

    protected fun safeLaunch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(handler, block = block)
    }

    companion object {
        private const val COROUTINE_EXCEPTION_HANDLER_MESSAGE = "ExceptionHandler"
    }
}