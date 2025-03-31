package dev.chirchir.domain.products.model

data class ProductsResponse(
    val products: List<Product>,
    val total: Int,
    val skip: Int,
    val limit: Int
)

data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val tags: List<String>,
    val brand: String? = null,
    val sku: String,
    val weight: Int,
    val dimensions: Dimensions,
    val warrantyInformation: String,
    val shippingInformation: String,
    val availabilityStatus: String,
    val reviews: List<Review>,
    val returnPolicy: String,
    val minimumOrderQuantity: Int,
    val meta: Meta,
    val images: List<String>,
    val thumbnail: String,
)

data class Dimensions(
    val width: Double,
    val height: Double,
    val depth: Double
)

data class Meta(
    val createdAt: String,
    val updatedAt: String,
    val barcode: String,
    val qrCode: String
)

data class Review(
    val rating: Int,
    val comment: String,
    val date: String,
    val reviewerName: String,
    val reviewerEmail: String
)