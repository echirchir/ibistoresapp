package dev.chirchir.ibistores.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dev.chirchir.data.common.InternetServiceImpl
import dev.chirchir.data.common.network.IBIHttpClient
import dev.chirchir.data.common.network.IBIHttpClientImpl
import dev.chirchir.data.products.datasource.ProductsDataSource
import dev.chirchir.data.products.datasource.remote.RemoteProductsDataSourceImpl
import dev.chirchir.data.products.repository.ProductsRepositoryImpl
import dev.chirchir.data.settings.SettingsRepositoryImpl
import dev.chirchir.domain.common.service.InternetService
import dev.chirchir.domain.products.repository.ProductsRepository
import dev.chirchir.domain.products.usecase.GetProductsUseCase
import dev.chirchir.domain.settings.repository.SettingsRepository
import dev.chirchir.domain.settings.usecase.GetDarkModeUseCase
import dev.chirchir.domain.settings.usecase.GetLanguageUseCase
import dev.chirchir.domain.settings.usecase.SetDarkModeUseCase
import dev.chirchir.domain.settings.usecase.SetLanguageUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val DataModule = module {
    factory<IBIHttpClient> { IBIHttpClientImpl() }
    single<ProductsRepository> { ProductsRepositoryImpl(get())}
    single<ProductsDataSource> { RemoteProductsDataSourceImpl(get())}
    single<SettingsRepository> { SettingsRepositoryImpl(get(), get()) }
}

val DomainModule = module {
    single<InternetService> { InternetServiceImpl(androidContext()) }
    factory { GetProductsUseCase(get(), get()) }
    factory { GetDarkModeUseCase(get()) }
    factory { SetDarkModeUseCase(get()) }
    factory { GetLanguageUseCase(get()) }
    factory { SetLanguageUseCase(get()) }
}

val dataStoreModule = module {
    single<DataStore<Preferences>> {
        val context = get<Context>()
        PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile("app_settings") },
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        )
    }
}