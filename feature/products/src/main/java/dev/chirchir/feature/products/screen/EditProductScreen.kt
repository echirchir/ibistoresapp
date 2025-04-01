package dev.chirchir.feature.products.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import dev.chirchir.core.ui.toast.MyToast
import dev.chirchir.domain.products.model.Product
import dev.chirchir.feature.products.R
import dev.chirchir.feature.products.viewmodels.EditProductViewModel
import org.koin.androidx.compose.getViewModel

@Composable
internal fun EditProductScreen(
    viewModel: EditProductViewModel = getViewModel(),
    product: Product,
    onBack: (Boolean) -> Unit
) {
    val state by viewModel.state.collectAsState()
    var showToast by remember { mutableStateOf(false) }

    LaunchedEffect(product) {
        viewModel.handleEvent(EditEvent.SetProduct(product))
    }

    ScreenLayout(
        color = MaterialTheme.colorScheme.background,
        header = {
            HeaderView(
                title = R.string.edit_product_title,
                onBack = { onBack(false) },
                color = MaterialTheme.colorScheme.background
            )
        },
        footer = {
            MyButton(
                enabled = state.canUpdate(),
                modifier = Modifier.padding(horizontal = 28.dp),
                text = R.string.update_product
            ) {
                viewModel.handleEvent(EditEvent.OnUpdate(onSuccess = { onBack(true) }, onFailure = {
                    showToast = true
                }))
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
                value = state.title,
                onChange = {
                    viewModel.handleEvent(EditEvent.OnNameChange(it))
                },
                isValid = state.title.isNotBlank(),
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
                value = state.price.toString(),
                onChange = {
                    viewModel.handleEvent(EditEvent.OnPriceChange(it))
                },
                isValid = state.price != 0.0,
                boldLabel = true,
                isPassword = false,
                showErrorState = true,
                label = "Price",
                enabled = true,
                hint = "Edit product price",
                fieldDescription = "",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }

    if(showToast) {
        MyToast.SweetError("Product editing failed")
        showToast = false
    }
}