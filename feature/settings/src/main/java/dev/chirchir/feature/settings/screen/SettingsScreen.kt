package dev.chirchir.feature.settings.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
    val context = LocalContext.current
    val state by viewModel.settingsUiState.collectAsState()

    ScreenLayout(
        color = MaterialTheme.colorScheme.background,
        header = {
            HeaderView(
                title = R.string.settings_title,
                color = MaterialTheme.colorScheme.background
            )
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
                LanguageSettingItem(
                    isHebrewLanguage = state.isHebrewLanguage,
                    onLanguageSelected = { isHebrew ->
                        if (isHebrew != state.isHebrewLanguage) {
                            viewModel.toggleLanguage(context)
                        }
                    }
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 16.dp),
                    color = MaterialTheme.colorScheme.outlineVariant
                )

                ThemeSettingItem(
                    isDarkMode = state.isDarkMode,
                    onThemeChanged = { viewModel.toggleTheme() }
                )
            }
        }
    }
}

@Composable
private fun LanguageSettingItem(
    isHebrewLanguage: Boolean,
    onLanguageSelected: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            Image(
                painter = painterResource(dev.chirchir.core.ui.R.drawable.ic_language),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                modifier = Modifier.size(24.dp)
            )

            Text(
                text = stringResource(id = R.string.language_setting),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp)
                .selectableGroup(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LanguageButton(
                text = stringResource(id = R.string.english),
                selected = !isHebrewLanguage,
                onClick = { onLanguageSelected(false) },
                modifier = Modifier.weight(1f)
            )

            LanguageButton(
                text = stringResource(id = R.string.hebrew),
                selected = isHebrewLanguage,
                onClick = { onLanguageSelected(true) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun LanguageButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
        contentColor = if (selected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
        modifier = modifier
            .selectable(
                selected = selected,
                onClick = onClick,
                role = Role.Button
            )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}

@Composable
private fun ThemeSettingItem(
    isDarkMode: Boolean,
    onThemeChanged: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(dev.chirchir.core.ui.R.drawable.ic_dark_mode),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
            modifier = Modifier.size(24.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.theme_setting),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = if (isDarkMode)
                    stringResource(id = R.string.dark_mode)
                else
                    stringResource(id = R.string.light_mode),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Switch(
            checked = isDarkMode,
            onCheckedChange = { onThemeChanged() },
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.primary,
                checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                uncheckedThumbColor = MaterialTheme.colorScheme.outline,
                uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        )
    }
}