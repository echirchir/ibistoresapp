package dev.chirchir.ibistores

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
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

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            IBIStoresTheme {
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