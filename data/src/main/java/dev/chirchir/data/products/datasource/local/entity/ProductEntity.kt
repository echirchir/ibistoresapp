package dev.chirchir.data.products.datasource.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "products", indices = [Index(value = ["id"], unique = true)])
data class ProductEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val brand: String,
    val thumbnail: String,
    val favorited: Boolean = false,
    val lastUpdated: Long = System.currentTimeMillis()
)