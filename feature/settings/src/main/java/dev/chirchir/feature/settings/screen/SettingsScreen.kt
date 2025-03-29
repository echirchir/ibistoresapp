package dev.chirchir.feature.settings.screen

import androidx.compose.runtime.Composable
import dev.chirchir.core.ui.components.HeaderView
import dev.chirchir.core.ui.components.ScreenLayout
import dev.chirchir.feature.settings.R

@Composable
internal fun SettingsScreen() {
    ScreenLayout(
        header = {
            HeaderView(title = R.string.settings_title)
        }
    ) {

    }
}