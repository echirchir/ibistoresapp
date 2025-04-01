package dev.chirchir.domain.products.repository

import dev.chirchir.domain.common.Response
import dev.chirchir.domain.products.model.Product
import dev.chirchir.domain.products.model.ProductsResponse
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {

    /**
     * Fetches products with network-first strategy, falling back to cache
     * @return Flow of Resource containing list of Products
     */
    fun getProducts(limit: Int, skip: Int): Flow<Response<ProductsResponse>>

    /**
     * Gets a single product by ID from local db
     * @param productId The ID of the product to retrieve
     * @return Resource containing the Product or error
     */
    suspend fun getProductById(productId: Int): Response<Product>

    /**
     * Updates a product in the database
     * @param product The product to update
     * @return Resource indicating success or failure
     */
    suspend fun updateProduct(product: Product): Response<Unit>

    /**
     * Deletes a product from the database
     * @param productId The ID of the product to delete
     * @return Resource indicating success or failure
     */
    suspend fun deleteProduct(productId: Int): Response<Unit>

    /**
     * Toggles the favorite status of a product
     * @param productId The ID of the product to toggle
     * @return Resource indicating success or failure
     */
    suspend fun toggleFavorite(productId: Int): Response<Unit>

    /**
     * Syncs local database with remote data source
     */
    suspend fun syncWithRemote()

    /**
     * Gets all favorited products
     * @return Flow of Resource containing favorited products
     */
    fun getFavorites(): Flow<Response<ProductsResponse>>
}