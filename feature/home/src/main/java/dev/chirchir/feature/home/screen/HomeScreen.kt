package dev.chirchir.feature.home.screen

import androidx.compose.runtime.Composable
import dev.chirchir.core.ui.components.HeaderView
import dev.chirchir.core.ui.components.ScreenLayout
import dev.chirchir.feature.home.R

@Composable
internal fun HomeScreen() {
    ScreenLayout(
        header = {
            HeaderView(title = R.string.home_title)
        }
    ) {

    }
}