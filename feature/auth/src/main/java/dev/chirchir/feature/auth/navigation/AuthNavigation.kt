package dev.chirchir.feature.auth.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import dev.chirchir.core.ui.common.extension.fromRightComposable
import dev.chirchir.feature.auth.screen.SignInScreen
import dev.chirchir.feature.home.navigation.navigateToHome

const val AUTH_HOME_ROUTE = "auth_home_route"

fun NavController.navigateToSignIn() = navigate(AUTH_HOME_ROUTE) { launchSingleTop = true }

fun NavGraphBuilder.authFeatureNavGraph(
    navController: NavController
) {
    fromRightComposable(
        route = AUTH_HOME_ROUTE
    ) {
        BackHandler(onBack = {})
        SignInScreen {
            navController.navigateToHome()
        }
    }
}

@Composable
fun AuthFeature(
    navController: NavController
) {
    BackHandler(onBack = {})
    SignInScreen {}
}