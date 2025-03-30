package dev.chirchir.feature.favorites.screen

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import dev.chirchir.core.ui.components.HeaderView
import dev.chirchir.core.ui.components.ScreenLayout
import dev.chirchir.feature.favorites.R
import dev.chirchir.feature.favorites.viewmodels.FavoritesViewModel
import org.koin.androidx.compose.getViewModel

@Composable
internal fun FavoritesScreen(
    viewModel: FavoritesViewModel = getViewModel()
) {
    ScreenLayout(
        color = MaterialTheme.colorScheme.background,
        header = {
            HeaderView(
                title = R.string.favorites_title,
                color = MaterialTheme.colorScheme.background
            )
        }
    ) {

    }
}