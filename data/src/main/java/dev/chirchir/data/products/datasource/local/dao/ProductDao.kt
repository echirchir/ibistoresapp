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
        val existingIds = products.map { it.id }
        val productsToDelete = getProductsNotInList(existingIds)
        productsToDelete.forEach { deleteProduct(it) }

        insertProducts(products)

        products.forEach { product ->
            val existing = getProductById(product.id)
            if (existing != null && existing != product) {
                updateProduct(product)
            }
        }
    }

    @Query("SELECT * FROM products WHERE favorited = 1 ORDER BY lastUpdated DESC")
    fun observeFavoritedProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE id NOT IN (:ids)")
    suspend fun getProductsNotInList(ids: List<Int>): List<ProductEntity>
}