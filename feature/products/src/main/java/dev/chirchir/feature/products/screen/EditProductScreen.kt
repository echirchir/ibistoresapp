package dev.chirchir.feature.products.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dev.chirchir.core.ui.components.HeaderView
import dev.chirchir.core.ui.components.IBITextField
import dev.chirchir.core.ui.components.MyButton
import dev.chirchir.core.ui.components.ScreenLayout
import dev.chirchir.domain.products.model.Product
import dev.chirchir.feature.products.R

@Composable
internal fun EditProductScreen(
    product: Product,
    onBack: () -> Unit
) {
    var productName by remember { mutableStateOf(product.title) }
    var productPrice by remember { mutableStateOf(product.price.toString()) }
    var priceError by remember { mutableStateOf(false) }

    ScreenLayout(
        color = MaterialTheme.colorScheme.background,
        header = {
            HeaderView(
                title = R.string.edit_product_title,
                onBack = onBack,
                color = MaterialTheme.colorScheme.background
            )
        },
        footer = {
            MyButton(
                enabled = productName.isNotBlank() && !priceError,
                modifier = Modifier.padding(horizontal = 28.dp),
                text = R.string.update_product
            ) {
                val price = productPrice.toDoubleOrNull() ?: product.price
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(vertical= paddingValues.calculateTopPadding(), horizontal = 24.dp)
                .imePadding()
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            IBITextField(
                value = productName,
                onChange = {},
                isValid = true,
                isPassword = false,
                showErrorState = true,
                label = "Name",
                boldLabel = true,
                enabled = true,
                hint = "Edit product name",
                fieldDescription = "",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
            )

            Spacer(modifier = Modifier.height(24.dp))

            IBITextField(
                value = productPrice,
                onChange = {
                    productPrice = it
                },
                isValid = true,
                boldLabel = true,
                isPassword = false,
                showErrorState = true,
                label = "Price",
                enabled = true,
                hint = "Edit product price",
                fieldDescription = "",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}