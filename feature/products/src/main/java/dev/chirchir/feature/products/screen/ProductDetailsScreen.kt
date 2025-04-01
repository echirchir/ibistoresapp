package dev.chirchir.feature.products.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.chirchir.core.ui.common.extension.isBiometricsAvailableOrEnabled
import dev.chirchir.core.ui.common.extension.showBiometricPrompt
import dev.chirchir.core.ui.components.HeaderView
import dev.chirchir.core.ui.toast.MyToast
import dev.chirchir.domain.products.model.Product
import dev.chirchir.feature.products.R
import dev.chirchir.feature.products.viewmodels.ProductDetailsViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.getViewModel

@Composable
fun ProductDetailScreen(
    viewModel: ProductDetailsViewModel = getViewModel(),
    product: Product,
    shouldRefresh: Boolean? = false,
    onEditClick: (Product) -> Unit,
    onDelete: () -> Unit,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    var showAuthErrorMessage by remember { mutableStateOf(false) }

    LaunchedEffect(product, shouldRefresh) {
        viewModel.handleEvent(DetailEvent.SetProduct(product))
        if(shouldRefresh == true) {
            viewModel.reloadProduct()
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            HeaderView(
                title = R.string.products_details_title,
                color = MaterialTheme.colorScheme.background,
                onBack = onBack
            )
        }
    ) { paddingValues ->
        if(showAuthErrorMessage) {
            MyToast.SweetError("Authentication failed. Operation not allowed")
            LaunchedEffect(Unit) {
                delay(300)
                showAuthErrorMessage = false
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
                .verticalScroll(scrollState)
                .background(MaterialTheme.colorScheme.background)
        ) {

            AsyncImage(
                model = state.product?.thumbnail,
                contentDescription = "Product image",
                contentScale = ContentScale.Crop,
                placeholder = rememberVectorPainter(Icons.Default.Image),
                error = rememberVectorPainter(Icons.Default.BrokenImage),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = state.product?.title ?: "",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = state.product?.description ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = state.product?.brand ?: "No brand",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    )

                    Text(
                        text = "$${"%.2f".format(state.product?.price)}",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(
                        enabled = context.isBiometricsAvailableOrEnabled(),
                        onClick = {
                            if(context.isBiometricsAvailableOrEnabled()) {
                                context.showBiometricPrompt(
                                    onSuccess = {
                                        onEditClick(product)
                                    },
                                    onError = {
                                        showAuthErrorMessage = true
                                    }
                                )
                            }
                        },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        Text("Edit")
                    }

                    OutlinedButton(
                        enabled = context.isBiometricsAvailableOrEnabled(),
                        onClick = {
                            if(context.isBiometricsAvailableOrEnabled()) {
                                context.showBiometricPrompt(
                                    onSuccess = {
                                        viewModel.handleEvent(DetailEvent.DeleteProduct(onDelete = onDelete, onFail = {}))
                                    },
                                    onError = {
                                        showAuthErrorMessage = true
                                    }
                                )
                            }
                        },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Delete")
                    }

                    IconButton(
                        onClick = {
                            viewModel.handleEvent(DetailEvent.FavoriteProduct)
                        },
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                shape = CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = if (state.product?.isFavorited == true) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                            contentDescription = "Favorite",
                            tint = if (state.product?.isFavorited == true) MaterialTheme.colorScheme.error
                            else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}