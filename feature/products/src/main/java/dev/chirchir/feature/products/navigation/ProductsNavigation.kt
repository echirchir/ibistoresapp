package dev.chirchir.feature.products.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import dev.chirchir.core.ui.common.extension.fromRightComposable
import dev.chirchir.feature.products.screen.ProductListScreen

private const val PRODUCTS_HOME_ROUTE = "products_home_route"
private const val PRODUCT_DETAILS_ROUTE = "product_details_route"
private const val EDIT_PRODUCT_ROUTE = "edit_product_route"

fun NavGraphBuilder.productsFeatureNavGraph(
    navController: NavController
) {
    fromRightComposable(
        route = PRODUCTS_HOME_ROUTE
    ) {
        ProductListScreen {

        }
    }
}

@Composable
fun ProductsFeature(
    navController: NavController
) {
    ProductListScreen {  }
}