package dev.chirchir.data.products.repository

import dev.chirchir.data.products.datasource.ProductsDataSource
import dev.chirchir.data.products.datasource.remote.model.toDomain
import dev.chirchir.domain.common.Response
import dev.chirchir.domain.products.model.ProductsResponse
import dev.chirchir.domain.products.repository.ProductsRepository

class ProductsRepositoryImpl(
    private val dataSource: ProductsDataSource
): ProductsRepository {
    override suspend fun getProducts(limit: Int, skip: Int): Response<ProductsResponse> {
        return try {
            Response.success(dataSource.getProducts(limit, skip).toDomain())
        } catch (e: Exception) {
            Response.failure(e)
        }
    }
}