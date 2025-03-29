package dev.chirchir.feature.favorites.screen

import androidx.compose.runtime.Composable
import dev.chirchir.core.ui.components.HeaderView
import dev.chirchir.core.ui.components.ScreenLayout
import dev.chirchir.feature.favorites.R

@Composable
internal fun FavoritesScreen() {
    ScreenLayout(
        header = {
            HeaderView(title = R.string.favorites_title)
        }
    ) {

    }
}