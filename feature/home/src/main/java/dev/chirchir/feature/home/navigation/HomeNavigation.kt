package dev.chirchir.feature.home.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import dev.chirchir.feature.home.screen.HomeScreen
import kotlinx.coroutines.launch

internal const val HOME_NAVIGATION = "home/home_navigation"
internal const val HOME_REFRESH = "home_refresh"

private const val PRODUCTS_ROUTE = "home/products"
private const val FAVORITES_ROUTE = "home/favorites"
private const val SETTINGS_ROUTE = "home/settings"

fun NavController.navigateToHome() = navigate(HOME_NAVIGATION) { popUpTo(HOME_NAVIGATION) { inclusive = true } }

private fun NavController.navigateToProducts() = navigate(PRODUCTS_ROUTE) { launchSingleTop = true }
private fun NavController.navigateToFavorites() = navigate(FAVORITES_ROUTE) { launchSingleTop = true }
private fun NavController.navigateToSettings() = navigate(SETTINGS_ROUTE) { launchSingleTop = true }

private fun NavController.getHomeRefresh(): Boolean? {
    val requireRefresh = currentBackStackEntry?.savedStateHandle?.get<Boolean?>(HOME_REFRESH)
    currentBackStackEntry?.savedStateHandle?.set(HOME_REFRESH, null)
    return requireRefresh
}

@OptIn(ExperimentalPagerApi::class)
fun NavGraphBuilder.homeFeatureNavGraph(
    navController: NavController,
    products: @Composable () -> Unit,
    favorites: @Composable () -> Unit,
    settings: @Composable () -> Unit
) {
    composable(
        route = HOME_NAVIGATION
    ) {
        val pagerState = rememberPagerState()
        val scope = rememberCoroutineScope()
        BackHandler(onBack = {})
        Scaffold(
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            bottomBar = {
                NavigationBarView(
                    pagerState = pagerState
                ) { page ->
                    scope.launch {
                        when (page) {
                            Pages.HOME -> pagerState.scrollToPage(0)
                            Pages.PRODUCTS -> pagerState.scrollToPage(1)
                            Pages.FAVORITES -> pagerState.scrollToPage(2)
                            Pages.SETTINGS -> pagerState.scrollToPage(3)
                        }
                    }
                }
            }
        ) {
            HorizontalPager(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = it.calculateBottomPadding()),
                state = pagerState,
                verticalAlignment = Alignment.Top,
                userScrollEnabled = false,
                count = 4,
            ) { position ->
                when(position) {
                    0 -> HomeScreen()
                    1 -> products()
                    2 -> favorites()
                    3 -> settings()
                }
            }
        }
    }
}