package dev.chirchir.ibistores

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dev.chirchir.core.ui.theme.IBIStoresTheme
import dev.chirchir.feature.auth.navigation.AUTH_HOME_ROUTE
import dev.chirchir.feature.auth.navigation.authFeatureNavGraph
import dev.chirchir.feature.favorites.navigation.FavoritesFeature
import dev.chirchir.feature.favorites.navigation.favoritesFeatureNavGraph
import dev.chirchir.feature.home.navigation.homeFeatureNavGraph
import dev.chirchir.feature.products.navigation.ProductsFeature
import dev.chirchir.feature.products.navigation.productsFeatureNavGraph
import dev.chirchir.feature.settings.navigation.SettingsFeature
import dev.chirchir.feature.settings.navigation.settingsFeatureNavGraph
import dev.chirchir.feature.settings.viewmodels.SettingsViewModel
import org.koin.android.ext.android.inject
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settingsViewModel: SettingsViewModel by inject()
            val uiState by settingsViewModel.settingsUiState.collectAsState()
            val navController = rememberNavController()
            IBIStoresTheme(
                darkTheme = uiState.isDarkMode
            ) {
                CompositionLocalProvider(
                    LocalContext provides updateLocale(uiState.isHebrewLanguage)
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = AUTH_HOME_ROUTE
                        ) {
                            authFeatureNavGraph(navController = navController)
                            homeFeatureNavGraph(
                                navController = navController,
                                products = {
                                    ProductsFeature(navController = navController)
                                },
                                favorites = {
                                    FavoritesFeature(navController = navController)
                                },
                                settings = {
                                    SettingsFeature(navController = navController)
                                }
                            )
                            productsFeatureNavGraph(navController = navController)
                            favoritesFeatureNavGraph(navController = navController)
                            settingsFeatureNavGraph(navController = navController)
                        }
                    }
                }
            }
        }
    }

    private fun updateLocale(isHebrew: Boolean): Context {
        val locale = if (isHebrew) Locale("he") else Locale.ENGLISH
        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        return createConfigurationContext(config)
    }
}