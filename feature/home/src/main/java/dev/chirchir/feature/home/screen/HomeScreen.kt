package dev.chirchir.feature.home.screen

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import dev.chirchir.core.ui.components.HeaderView
import dev.chirchir.core.ui.components.ScreenLayout
import dev.chirchir.core.ui.theme.IBIStoresTheme
import dev.chirchir.feature.home.R
import dev.chirchir.feature.home.viewmodels.HomeViewModel
import org.koin.androidx.compose.getViewModel

@Composable
internal fun HomeScreen(
    viewModel: HomeViewModel = getViewModel()
) {
    ScreenLayout(
        color = MaterialTheme.colorScheme.background,
        header = {
            HeaderView(
                title = R.string.home_title,
                color = MaterialTheme.colorScheme.background
            )
        }
    ) {

    }
}