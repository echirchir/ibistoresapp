package dev.chirchir.data.products.datasource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import dev.chirchir.data.products.datasource.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProducts(products: List<ProductEntity>)

    @Update
    suspend fun updateProduct(product: ProductEntity)

    @Query("UPDATE products SET favorited = :isFavorited WHERE id = :productId")
    suspend fun updateFavoriteStatus(productId: Int, isFavorited: Boolean)

    @Delete
    suspend fun deleteProduct(product: ProductEntity)

    @Query("DELETE FROM products WHERE id = :productId")
    suspend fun deleteProductById(productId: Int)

    @Query("SELECT * FROM products ORDER BY lastUpdated DESC")
    fun observeAllProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE id = :productId")
    suspend fun getProductById(productId: Int): ProductEntity?

    @Transaction
    suspend fun syncProducts(products: List<ProductEntity>) {
        // First delete products not in the new list
        val existingIds = products.map { it.id }
        // This is more efficient than deleting all and reinserting
        // Only works if you have a small dataset (<1000 items)
        // For larger datasets, consider a different approach
        val productsToDelete = getProductsNotInList(existingIds)
        productsToDelete.forEach { deleteProduct(it) }

        // Then insert/update the new products
        insertProducts(products)

        // Update existing products that might have changed
        products.forEach { product ->
            val existing = getProductById(product.id)
            if (existing != null && existing != product) {
                updateProduct(product)
            }
        }
    }

    @Query("SELECT * FROM products WHERE id NOT IN (:ids)")
    suspend fun getProductsNotInList(ids: List<Int>): List<ProductEntity>
}