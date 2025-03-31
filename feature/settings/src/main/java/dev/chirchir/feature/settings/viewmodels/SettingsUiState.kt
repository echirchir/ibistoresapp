package dev.chirchir.feature.settings.viewmodels

data class SettingsUiState(
    val isDarkMode: Boolean = false,
    val isHebrewLanguage: Boolean = false,
    val isLoading: Boolean = true,
    val error: String? = null
)