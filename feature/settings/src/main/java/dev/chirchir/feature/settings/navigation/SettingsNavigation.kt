package dev.chirchir.feature.settings.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import dev.chirchir.core.ui.common.extension.fromRightComposable
import dev.chirchir.feature.auth.navigation.navigateToSignIn
import dev.chirchir.feature.settings.screen.SettingsScreen

private const val SETTINGS_HOME_ROUTE = "settings_home_route"

fun NavController.navigateToSettingsHome() = navigate(SETTINGS_HOME_ROUTE) { launchSingleTop = true }

fun NavGraphBuilder.settingsFeatureNavGraph(
    navController: NavController
) {
    fromRightComposable(
        route = SETTINGS_HOME_ROUTE
    ) {
        BackHandler(onBack = {})
        SettingsScreen(onSignOut = { navController.navigateToSignIn() })
    }
}

@Composable
fun SettingsFeature(
    navController: NavController
) {
    BackHandler(onBack = {})
    SettingsScreen(onSignOut = { navController.navigateToSignIn() })
}