package dev.chirchir.data.products.datasource.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import dev.chirchir.domain.products.model.Product

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

fun Product.toEntity(): ProductEntity {
    return ProductEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        price = this.price,
        brand = this.brand ?: "",
        thumbnail = this.thumbnail,
        favorited = this.isFavorited
    )
}

fun ProductEntity.toDomain(): Product {
    return Product(
        id = this.id,
        title = this.title,
        description = this.description,
        price = this.price,
        brand = this.brand,
        thumbnail = this.thumbnail,
        isFavorited = this.favorited
    )
}