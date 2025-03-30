package dev.chirchir.feature.settings.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.chirchir.core.ui.components.HeaderView
import dev.chirchir.core.ui.components.MyButton
import dev.chirchir.core.ui.components.ScreenLayout
import dev.chirchir.feature.settings.R
import dev.chirchir.feature.settings.viewmodels.SettingsViewModel
import org.koin.androidx.compose.getViewModel

@Composable
internal fun SettingsScreen(
    viewModel: SettingsViewModel = getViewModel(),
    onSignOut: () -> Unit
) {
    val isDarkMode by viewModel.isDarkMode.collectAsState()
    val isHebrewLanguage by viewModel.isHebrewLanguage.collectAsState()

    ScreenLayout(
        color = MaterialTheme.colorScheme.background,
        header = {
            HeaderView(title = R.string.settings_title, color = MaterialTheme.colorScheme.background)
        },
        footer = {
            MyButton(
                modifier = Modifier.padding(horizontal = 28.dp),
                text = R.string.sign_out_title,
                onClick = onSignOut
            )
        }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
                .padding(top = it.calculateTopPadding() + 24.dp)
                .padding(horizontal = 8.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {

                SettingItem(
                    icon = Icons.Default.LocationOn,
                    title = stringResource(id = R.string.language_setting),
                    subtitle = if (isHebrewLanguage)
                        stringResource(id = R.string.hebrew)
                    else
                        stringResource(id = R.string.english),
                    isChecked = isHebrewLanguage,
                    onCheckedChange = {
                        viewModel.toggleLanguage()
                    }
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 16.dp),
                    color = MaterialTheme.colorScheme.outlineVariant
                )

                SettingItem(
                    icon = Icons.Default.Build,
                    title = stringResource(id = R.string.theme_setting),
                    subtitle = if (isDarkMode)
                        stringResource(id = R.string.dark_mode)
                    else
                        stringResource(id = R.string.light_mode),
                    isChecked = isDarkMode,
                    onCheckedChange = { viewModel.toggleTheme() }
                )
            }
        }
    }
}

@Composable
private fun SettingItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.primary,
                checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                uncheckedThumbColor = MaterialTheme.colorScheme.outline,
                uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        )
    }
}