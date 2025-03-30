package dev.chirchir.feature.favorites.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import dev.chirchir.feature.favorites.viewmodels.FavoritesViewModel

val FavoritesFeatModule = module {
    viewModelOf(::FavoritesViewModel)
}