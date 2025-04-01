package dev.chirchir.feature.products.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import dev.chirchir.core.ui.common.extension.fromJson
import dev.chirchir.core.ui.common.extension.fromRightComposable
import dev.chirchir.core.ui.common.extension.toJson
import dev.chirchir.domain.products.model.Product
import dev.chirchir.feature.products.screen.EditProductScreen
import dev.chirchir.feature.products.screen.ProductDetailScreen
import dev.chirchir.feature.products.screen.ProductListScreen

private const val SELECTED_PRODUCT = "selected_product"
private const val EDITING_PRODUCT = "editing_product"
private const val SHOULD_REFRESH_AFTER_EDIT = "should_refresh_after_edit"

private const val PRODUCTS_HOME_ROUTE = "products_home_route"
private const val PRODUCT_DETAILS_ROUTE = "product_details_route"
private const val EDIT_PRODUCT_ROUTE = "edit_product_route"

private fun NavController.navigateToProductDetails() = navigate(PRODUCT_DETAILS_ROUTE) { launchSingleTop = true }
private fun NavController.navigateToEditProduct() = navigate(EDIT_PRODUCT_ROUTE) { launchSingleTop = true }

fun NavGraphBuilder.productsFeatureNavGraph(
    navController: NavController
) {
    fromRightComposable(
        route = PRODUCTS_HOME_ROUTE
    ) {
        BackHandler(onBack = {})
        ProductListScreen(onProductSelected = { product ->
            navController.currentBackStackEntry?.savedStateHandle?.set(SELECTED_PRODUCT, product.toJson())
            navController.navigateToProductDetails()
        })
    }

    fromRightComposable(
        route = PRODUCT_DETAILS_ROUTE
    ) {
        BackHandler(onBack = navController::popBackStack)
        val product = navController.previousBackStackEntry?.savedStateHandle?.get<String>(
            SELECTED_PRODUCT)?.fromJson<Product>()
        val refreshAfterEdit = navController.currentBackStackEntry?.savedStateHandle?.get<Boolean>(
            SHOULD_REFRESH_AFTER_EDIT)
        product?.let {
            ProductDetailScreen(
                product = it,
                shouldRefresh = refreshAfterEdit,
                onDelete = navController::popBackStack,
                onEditClick = { toEdit ->
                    navController.currentBackStackEntry?.savedStateHandle?.set(EDITING_PRODUCT, toEdit.toJson())
                    navController.navigateToEditProduct()
                },
                onBack = navController::popBackStack
            )
        }
    }

    fromRightComposable(
        route = EDIT_PRODUCT_ROUTE
    ) {
        BackHandler(onBack = navController::popBackStack)
        val product = navController.previousBackStackEntry?.savedStateHandle?.get<String>(
            EDITING_PRODUCT)?.fromJson<Product>()
        product?.let {
            EditProductScreen(
                product = it,
                onBack = { shouldRefresh ->
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        SHOULD_REFRESH_AFTER_EDIT, shouldRefresh)
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
fun ProductsFeature(
    navController: NavController
) {
    BackHandler(onBack = {})
    ProductListScreen(onProductSelected = { product ->
        navController.currentBackStackEntry?.savedStateHandle?.set(SELECTED_PRODUCT, product.toJson())
        navController.navigateToProductDetails()
    })
}