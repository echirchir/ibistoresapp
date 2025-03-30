package dev.chirchir.domain.products.repository

import dev.chirchir.domain.common.Response
import dev.chirchir.domain.products.model.ProductResponse

interface ProductsRepository {
    suspend fun getProducts(): Response<ProductResponse>
}