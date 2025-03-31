package dev.chirchir.feature.settings.viewmodels

import dev.chirchir.core.ui.base.BaseViewModel
import dev.chirchir.core.ui.base.UiEvent
import dev.chirchir.core.ui.base.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class SettingsViewModel(

) : BaseViewModel<UiState<Any>, UiEvent>(){
    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    private val _isHebrewLanguage = MutableStateFlow(false)
    val isHebrewLanguage: StateFlow<Boolean> = _isHebrewLanguage.asStateFlow()

    init {
        safeLaunch {
            // Initialize with saved preferences
            _isDarkMode.value = true // settingsRepository.isDarkModeEnabled().first()
            _isHebrewLanguage.value = false // settingsRepository.getCurrentLanguage().first() == "he"
        }
    }

    fun toggleTheme() {
        val newValue = !_isDarkMode.value
        _isDarkMode.value = newValue
        safeLaunch  {
            // settingsRepository.setDarkModeEnabled(newValue)
        }
    }

    fun toggleLanguage() {
        val newValue = !_isHebrewLanguage.value
        _isHebrewLanguage.value = newValue
        safeLaunch {
            // settingsRepository.setCurrentLanguage(if (newValue) "he" else "en")
        }
    }
}