package dev.chirchir.domain.settings.usecase

import dev.chirchir.domain.common.Response
import dev.chirchir.domain.common.usecase.UseCase
import dev.chirchir.domain.settings.repository.SettingsRepository

class SetDarkModeUseCase(
    private val settingsRepository: SettingsRepository
): UseCase<Boolean, Boolean> {
    override suspend fun execute(input: Boolean): Response<Boolean>{
        return settingsRepository.setDarkModeEnabled(input)
    }
}