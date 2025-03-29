package dev.chirchir.feature.favorites.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import dev.chirchir.core.ui.common.extension.fromRightComposable
import dev.chirchir.feature.favorites.screen.FavoritesScreen

private const val FAVORITES_HOME_ROUTE = "favorites_home_route"

fun NavController.navigateToCardsHome() = navigate(FAVORITES_HOME_ROUTE) { launchSingleTop = true }

fun NavGraphBuilder.favoritesFeatureNavGraph(
    navController: NavController
) {
    fromRightComposable(
        route = FAVORITES_HOME_ROUTE
    ) {
        BackHandler(onBack = {})
        FavoritesScreen()
    }
}

@Composable
fun FavoritesFeature(
    navController: NavController
) {
    BackHandler(onBack = {})
    FavoritesScreen()
}