package dev.chirchir.data.products.datasource

import dev.chirchir.data.products.datasource.remote.model.ProductsResponseData

interface ProductsDataSource {
    suspend fun getProducts(): ProductsResponseData
}