package dev.chirchir.feature.products.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import dev.chirchir.feature.products.viewmodels.ProductsViewModel

val ProductsFeatureModule = module {
    viewModelOf(::ProductsViewModel)
}