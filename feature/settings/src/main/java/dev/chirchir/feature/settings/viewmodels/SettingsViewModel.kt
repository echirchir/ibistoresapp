package dev.chirchir.feature.settings.viewmodels

import dev.chirchir.core.ui.base.BaseViewModel
import dev.chirchir.core.ui.base.UiEvent
import dev.chirchir.core.ui.base.UiState
import dev.chirchir.domain.common.Response
import dev.chirchir.domain.settings.usecase.GetDarkModeUseCase
import dev.chirchir.domain.settings.usecase.GetLanguageUseCase
import dev.chirchir.domain.settings.usecase.SetDarkModeUseCase
import dev.chirchir.domain.settings.usecase.SetLanguageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel(
    private val setLanguageUseCase: SetLanguageUseCase,
    private val getLanguageUseCase: GetLanguageUseCase,
    private val setDarkModeUseCase: SetDarkModeUseCase,
    private val getDarkModeUseCase: GetDarkModeUseCase
) : BaseViewModel<UiState<Any>, UiEvent>(){

    private val _settingsUiState = MutableStateFlow(SettingsUiState())
    val settingsUiState: StateFlow<SettingsUiState> = _settingsUiState.asStateFlow()

    init {
        loadSettings()
    }

    private fun loadSettings() {
        safeLaunch {
            getDarkModeUseCase.execute().collect { response ->
                when (response) {
                    is Response.Success -> {
                        _settingsUiState.value = _settingsUiState.value.copy(
                            isDarkMode = response.data,
                            isLoading = false
                        )
                    }
                    is Response.Failure -> {
                        _settingsUiState.value = _settingsUiState.value.copy(
                            error = "",
                            isLoading = false
                        )
                    }
                }
            }

            getLanguageUseCase.execute().collect { response ->
                when (response) {
                    is Response.Success -> {
                        _settingsUiState.value = _settingsUiState.value.copy(
                            isHebrewLanguage = response.data == "he",
                            isLoading = false
                        )
                    }
                    is Response.Failure -> {
                        _settingsUiState.value = _settingsUiState.value.copy(
                            error = "",
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun toggleTheme() {
        safeLaunch {
            val newValue = !settingsUiState.value.isDarkMode
            _settingsUiState.value = _settingsUiState.value.copy(isDarkMode = newValue)
            setDarkModeUseCase.execute(newValue)
        }
    }

    fun toggleLanguage() {
        safeLaunch {
            val newLanguage = if (settingsUiState.value.isHebrewLanguage) "en" else "he"
            _settingsUiState.value = _settingsUiState.value.copy(isHebrewLanguage = !settingsUiState.value.isHebrewLanguage)
            setLanguageUseCase.execute(newLanguage)
        }
    }
}