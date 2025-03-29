package dev.chirchir.core.ui.common.extension

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable


data class FixedInsets(
    val statusBarHeight: Dp = 0.dp,
    val navigationBarsPadding: PaddingValues = PaddingValues(),
)

fun NavGraphBuilder.fromBottomComposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    popEnterTransition: Boolean = false,
    exitTransition: Boolean = false,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) = composable(
    route = route,
    arguments = arguments,
    popEnterTransition = {
        if (!popEnterTransition) null
        else return@composable slideIntoContainer(
            AnimatedContentTransitionScope.SlideDirection.End, tween(220)
        )
    },
    enterTransition = {
        return@composable slideIntoContainer(
            AnimatedContentTransitionScope.SlideDirection.Up, tween(340)
        )
    },
    popExitTransition = {
        return@composable slideOutOfContainer(
            AnimatedContentTransitionScope.SlideDirection.Down, tween(400)
        )
    },
    exitTransition = {
        if (!exitTransition) null
        else return@composable slideOutOfContainer(
            AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
        )
    },
    content = content,
)

fun NavGraphBuilder.fromRightComposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    popEnterTransition: Boolean = false,
    exitTransition: Boolean = false,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) = composable(
    route = route,
    arguments = arguments,
    popEnterTransition = {
        if (!popEnterTransition) null
        else return@composable slideIntoContainer(
            AnimatedContentTransitionScope.SlideDirection.End, tween(220)
        )
    },
    enterTransition = {
        return@composable slideIntoContainer(
            AnimatedContentTransitionScope.SlideDirection.Start, tween(340)
        )
    },
    popExitTransition = {
        return@composable slideOutOfContainer(
            AnimatedContentTransitionScope.SlideDirection.End, tween(340)
        )
    },
    exitTransition = {
        if (!exitTransition) null
        else return@composable slideOutOfContainer(
            AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
        )
    },
    content = content,
)

fun NavGraphBuilder.nullAnimationComposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) = composable(
    route = route,
    arguments = arguments,
    popEnterTransition = null,
    enterTransition = null,
    popExitTransition = null,
    exitTransition = null,
    content = content,
)