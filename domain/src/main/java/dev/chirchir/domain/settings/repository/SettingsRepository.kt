package dev.chirchir.domain.settings.repository

import dev.chirchir.domain.common.Response
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun setDarkModeEnabled(enabled: Boolean): Response<Boolean>
    fun getDarkModeEnabled(): Flow<Response<Boolean>>

    suspend fun setAppLanguage(languageCode: String): Response<Boolean>
    fun getAppLanguage(): Flow<Response<String>>
}