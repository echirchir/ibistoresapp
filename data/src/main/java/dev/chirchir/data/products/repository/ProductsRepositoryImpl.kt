package dev.chirchir.data.products.repository

import dev.chirchir.data.products.datasource.ProductsDataSource
import dev.chirchir.data.products.datasource.remote.model.toDomain
import dev.chirchir.domain.common.Response
import dev.chirchir.domain.products.model.ProductResponse
import dev.chirchir.domain.products.repository.ProductsRepository

class ProductsRepositoryImpl(
    private val dataSource: ProductsDataSource
): ProductsRepository {
    override suspend fun getProducts(): Response<ProductResponse> {
        return try {
            Response.success(dataSource.getProducts().toDomain())
        } catch (e: Exception) {
            Response.failure(e)
        }
    }
}