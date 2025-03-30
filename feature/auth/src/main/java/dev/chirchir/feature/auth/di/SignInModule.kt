package dev.chirchir.feature.auth.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import dev.chirchir.feature.auth.viewmodels.SignInViewModel

val AuthFeatModule = module {
    viewModelOf(::SignInViewModel)
}