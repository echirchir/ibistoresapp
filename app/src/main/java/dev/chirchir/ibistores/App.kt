package dev.chirchir.ibistores

import android.app.Application
import dev.chirchir.feature.auth.di.AuthFeatModule
import dev.chirchir.feature.favorites.di.FavoritesFeatModule
import dev.chirchir.feature.home.di.HomeFeatModule
import dev.chirchir.feature.products.di.ProductsFeatureModule
import dev.chirchir.feature.settings.di.SettingsFeatModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            printLogger()
            androidContext(this@App)
            modules(
                HomeFeatModule,
                AuthFeatModule,
                FavoritesFeatModule,
                ProductsFeatureModule,
                SettingsFeatModule
            )
        }
    }
}