package dev.chirchir.domain.settings.usecase

import dev.chirchir.domain.common.Response
import dev.chirchir.domain.common.usecase.UseCaseNoInputFlow
import dev.chirchir.domain.settings.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetLanguageUseCase(
    private val settingsRepository: SettingsRepository
): UseCaseNoInputFlow<String> {
    override fun execute(): Flow<Response<String>> {
        return settingsRepository.getAppLanguage()
    }
}