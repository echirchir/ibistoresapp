package dev.chirchir.domain.settings.usecase

import dev.chirchir.domain.common.Response
import dev.chirchir.domain.common.usecase.UseCase
import dev.chirchir.domain.settings.repository.SettingsRepository

class SetLanguageUseCase(
    private val settingsRepository: SettingsRepository
): UseCase<String, Boolean> {
    override suspend fun execute(input: String): Response<Boolean> {
        return settingsRepository.setAppLanguage(input)
    }
}