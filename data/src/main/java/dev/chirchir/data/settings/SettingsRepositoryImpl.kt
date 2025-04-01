package dev.chirchir.data.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.chirchir.domain.common.Response
import dev.chirchir.domain.settings.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
    private val context: Context
): SettingsRepository {

    companion object {
        private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode_enabled")
        private val LANGUAGE_KEY = stringPreferencesKey("app_language")
    }

    override suspend fun setDarkModeEnabled(enabled: Boolean): Response<Boolean> {
        dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = enabled
        }
        return Response.Success(true)
    }

    override fun getDarkModeEnabled(): Flow<Response<Boolean>> {
        return dataStore.data
            .map<Preferences, Response<Boolean>> { preferences ->
                Response.Success(preferences[DARK_MODE_KEY] ?: false)
            }
            .catch { exception ->
                emit(Response.Failure(Exception("Failed to read dark mode preference", exception)))
            }
    }

    override suspend fun setAppLanguage(languageCode: String): Response<Boolean> {

        dataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = languageCode
        }
        return Response.Success(true)
    }

    override fun getAppLanguage(): Flow<Response<String>> {
        return dataStore.data
            .map<Preferences, Response<String>> { preferences ->
                Response.Success(preferences[LANGUAGE_KEY] ?: "en")
            }
            .catch { exception ->
                emit(Response.Failure(Exception("Failed to read language preference", exception)))
            }
    }
}