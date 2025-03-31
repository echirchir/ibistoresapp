package dev.chirchir.ibistores.di

import dev.chirchir.data.common.InternetServiceImpl
import dev.chirchir.data.common.network.IBIHttpClient
import dev.chirchir.data.common.network.IBIHttpClientImpl
import dev.chirchir.data.products.datasource.ProductsDataSource
import dev.chirchir.data.products.datasource.remote.RemoteProductsDataSourceImpl
import dev.chirchir.data.products.repository.ProductsRepositoryImpl
import dev.chirchir.domain.common.service.InternetService
import dev.chirchir.domain.products.repository.ProductsRepository
import dev.chirchir.domain.products.usecase.GetProductsUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val DataModule = module {
    factory<IBIHttpClient> { IBIHttpClientImpl() }
    single<ProductsRepository> { ProductsRepositoryImpl(get())}
    single<ProductsDataSource> { RemoteProductsDataSourceImpl(get())}
}

val DomainModule = module {
    single<InternetService> { InternetServiceImpl(androidContext()) }
    factory { GetProductsUseCase(get(), get()) }
}