package dev.chirchir.feature.home.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import dev.chirchir.feature.home.viewmodels.HomeViewModel

val HomeFeatModule = module {
    viewModelOf(::HomeViewModel)
}