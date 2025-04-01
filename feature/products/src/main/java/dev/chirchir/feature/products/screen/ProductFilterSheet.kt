package dev.chirchir.feature.products.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.chirchir.core.ui.components.MyButton
import dev.chirchir.feature.products.R

enum class FilterOption {
    PRICE_ASCENDING, PRICE_DESCENDING, NAME_ASCENDING, NAME_DESCENDING, NONE
}

@Composable
internal fun ProductFilterSheet(
    option: FilterOption = FilterOption.NONE,
    onApplyFilter: (selectedOption: FilterOption) -> Unit
) {
    var selectedOption by remember { mutableStateOf(option) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.filter_by_title),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
            RadioOption(
                text = "No Filter",
                selected = selectedOption == FilterOption.NONE,
                onSelect = { selectedOption = FilterOption.NONE }
            )
            RadioOption(
                text = "Price ASC",
                selected = selectedOption == FilterOption.PRICE_ASCENDING,
                onSelect = { selectedOption = FilterOption.PRICE_ASCENDING }
            )
            RadioOption(
                text = "Price DESC",
                selected = selectedOption == FilterOption.PRICE_DESCENDING,
                onSelect = { selectedOption = FilterOption.PRICE_DESCENDING }
            )
            RadioOption(
                text = "Name ASC",
                selected = selectedOption == FilterOption.NAME_ASCENDING,
                onSelect = { selectedOption = FilterOption.NAME_ASCENDING }
            )
            RadioOption(
                text = "Name DESC",
                selected = selectedOption == FilterOption.NAME_DESCENDING,
                onSelect = { selectedOption = FilterOption.NAME_DESCENDING }
            )
        }

        MyButton(
            text = R.string.apply_filter_title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp)
                .padding(top = 16.dp),
            onClick = { onApplyFilter(selectedOption) },
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun RadioOption(
    text: String,
    selected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() }
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
            colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}