package dev.chirchir.data.products.datasource.remote.model

import dev.chirchir.domain.products.model.Dimensions
import dev.chirchir.domain.products.model.Meta
import dev.chirchir.domain.products.model.Product
import dev.chirchir.domain.products.model.ProductsResponse
import dev.chirchir.domain.products.model.Review
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ProductsResponseData(
    @SerialName("products")
    val products: List<ProductResponseData>? = null,
    @SerialName("total")
    val total: Int = 0,
    @SerialName("skip")
    val skip: Int = 0,
    @SerialName("limit")
    val limit: Int = 0
)

@Serializable
data class ProductResponseData(
    @SerialName("id")
    val id: Int? = 0,
    @SerialName("title")
    val title: String? = "",
    @SerialName("description")
    val description: String? = "",
    @SerialName("category")
    val category: String? = "",
    @SerialName("price")
    val price: Double? = 0.0,
    @SerialName("discountPercentage")
    val discountPercentage: Double? = 0.0,
    @SerialName("rating")
    val rating: Double? = 0.0,
    @SerialName("stock")
    val stock: Int? = 0,
    @SerialName("tags")
    val tags: List<String> = emptyList(),
    @SerialName("brand")
    val brand: String? = null,
    @SerialName("sku")
    val sku: String? = "",
    @SerialName("weight")
    val weight: Int? = 0,
    @SerialName("dimensions")
    val dimensions: DimensionsData? = null,
    @SerialName("warrantyInformation")
    val warrantyInformation: String? = "",
    @SerialName("shippingInformation")
    val shippingInformation: String? = "",
    @SerialName("availabilityStatus")
    val availabilityStatus: String? = "",
    @SerialName("reviews")
    val reviews: List<ReviewData> = emptyList(),
    @SerialName("returnPolicy")
    val returnPolicy: String? = "",
    @SerialName("minimumOrderQuantity")
    val minimumOrderQuantity: Int? = 0,
    @SerialName("meta")
    val meta: MetaData? = null,
    @SerialName("images")
    val images: List<String> = emptyList(),
    @SerialName("thumbnail")
    val thumbnail: String? = "",
)

@Serializable
data class DimensionsData(
    @SerialName("width")
    val width: Double? = 0.0,
    @SerialName("height")
    val height: Double? = 0.0,
    @SerialName("depth")
    val depth: Double? = 0.0
)

@Serializable
data class MetaData(
    @SerialName("createdAt")
    val createdAt: String? = "",
    @SerialName("updatedAt")
    val updatedAt: String? = "",
    @SerialName("barcode")
    val barcode: String? = "",
    @SerialName("qrCode")
    val qrCode: String? = ""
)

@Serializable
data class ReviewData(
    @SerialName("rating")
    val rating: Int? = 0,
    @SerialName("comment")
    val comment: String? = "",
    @SerialName("date")
    val date: String? = "",
    @SerialName("reviewerName")
    val reviewerName: String? = "",
    @SerialName("reviewerEmail")
    val reviewerEmail: String? = ""
)

internal fun ProductsResponseData.toDomain(): ProductsResponse {
    return ProductsResponse(
        products = products?.mapNotNull { it.toDomain() }.orEmpty(),
        total = total,
        skip = skip,
        limit = limit
    )
}

internal fun ProductResponseData.toDomain() = Product(
    id = id ?: 0,
    title = title ?: "",
    description = description ?: "",
    category = category ?: "",
    price = price ?: 0.0,
    discountPercentage = discountPercentage ?: 0.0,
    rating = rating ?: 0.0,
    stock = stock ?: 0,
    tags = tags,
    brand = brand,
    sku = sku ?: "",
    weight = weight ?: 0,
    dimensions = dimensions?.toDomain(),
    warrantyInformation = warrantyInformation ?: "",
    shippingInformation = shippingInformation ?: "",
    availabilityStatus = availabilityStatus ?: "",
    reviews = reviews.map { it.toDomain() },
    returnPolicy = returnPolicy ?: "",
    minimumOrderQuantity = minimumOrderQuantity ?: 0,
    meta = meta?.toDomain(),
    images = images,
    thumbnail = thumbnail ?: ""
)

internal fun DimensionsData.toDomain() = Dimensions(
    width = width ?: 0.0,
    height = height ?: 0.0,
    depth = depth ?: 0.0
)

internal fun MetaData.toDomain() = Meta(
    createdAt = createdAt ?: "",
    updatedAt = updatedAt ?: "",
    barcode = barcode ?: "",
    qrCode = qrCode ?: ""
)

internal fun ReviewData.toDomain() = Review(
    rating = rating ?: 0,
    comment = comment ?: "",
    date = date ?: "",
    reviewerName = reviewerName ?: "",
    reviewerEmail = reviewerEmail ?: ""
)