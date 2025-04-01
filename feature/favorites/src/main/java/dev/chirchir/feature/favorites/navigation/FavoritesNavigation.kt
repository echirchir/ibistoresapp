package dev.chirchir.feature.favorites.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import dev.chirchir.core.ui.common.extension.fromRightComposable
import dev.chirchir.core.ui.common.extension.toJson
import dev.chirchir.feature.favorites.screen.FavoritesScreen
import dev.chirchir.feature.products.navigation.navigateToProductDetails

private const val SELECTED_PRODUCT = "selected_product"
private const val FAVORITES_HOME_ROUTE = "favorites_home_route"

fun NavGraphBuilder.favoritesFeatureNavGraph(
    navController: NavController
) {
    fromRightComposable(
        route = FAVORITES_HOME_ROUTE
    ) {
        BackHandler(onBack = {})
        FavoritesScreen(
            onSelect = { product ->
                navController.currentBackStackEntry?.savedStateHandle?.set(SELECTED_PRODUCT, product.toJson())
                navController.navigateToProductDetails()
            }
        )
    }
}

@Composable
fun FavoritesFeature(
    navController: NavController
) {
    BackHandler(onBack = {})
    FavoritesScreen(
        onSelect = { product ->
            navController.currentBackStackEntry?.savedStateHandle?.set(SELECTED_PRODUCT, product.toJson())
            navController.navigateToProductDetails()
        }
    )
}