package dev.chirchir.feature.settings.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import dev.chirchir.feature.settings.viewmodels.SettingsViewModel

val SettingsFeatModule = module {
    viewModelOf(::SettingsViewModel)
}