package dev.chirchir.feature.products.di

import dev.chirchir.feature.products.viewmodels.EditProductViewModel
import dev.chirchir.feature.products.viewmodels.ProductsViewModel
import dev.chirchir.feature.products.viewmodels.ProductDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val ProductsFeatureModule = module {
    viewModelOf(::ProductsViewModel)
    viewModelOf(::EditProductViewModel)
    viewModelOf(::ProductDetailsViewModel)
}