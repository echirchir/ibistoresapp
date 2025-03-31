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
    val products: List<ProductResponseData>,
    @SerialName("total")
    val total: Int,
    @SerialName("skip")
    val skip: Int,
    @SerialName("limit")
    val limit: Int
)

@Serializable
data class ProductResponseData(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("category")
    val category: String,
    @SerialName("price")
    val price: Double,
    @SerialName("discountPercentage")
    val discountPercentage: Double,
    @SerialName("rating")
    val rating: Double,
    @SerialName("stock")
    val stock: Int,
    @SerialName("tags")
    val tags: List<String>,
    @SerialName("brand")
    val brand: String? = null,
    @SerialName("sku")
    val sku: String,
    @SerialName("weight")
    val weight: Int,
    @SerialName("dimensions")
    val dimensions: DimensionsData,
    @SerialName("warrantyInformation")
    val warrantyInformation: String,
    @SerialName("shippingInformation")
    val shippingInformation: String,
    @SerialName("availabilityStatus")
    val availabilityStatus: String,
    @SerialName("reviews")
    val reviews: List<ReviewData>,
    @SerialName("returnPolicy")
    val returnPolicy: String,
    @SerialName("minimumOrderQuantity")
    val minimumOrderQuantity: Int,
    @SerialName("meta")
    val meta: MetaData,
    @SerialName("images")
    val images: List<String>,
    @SerialName("thumbnail")
    val thumbnail: String,
)

@Serializable
data class DimensionsData(
    @SerialName("width")
    val width: Double,
    @SerialName("height")
    val height: Double,
    @SerialName("depth")
    val depth: Double
)

@Serializable
data class MetaData(
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("updatedAt")
    val updatedAt: String,
    @SerialName("barcode")
    val barcode: String,
    @SerialName("qrCode")
    val qrCode: String
)

@Serializable
data class ReviewData(
    @SerialName("rating")
    val rating: Int,
    @SerialName("comment")
    val comment: String,
    @SerialName("date")
    val date: String,
    @SerialName("reviewerName")
    val reviewerName: String,
    @SerialName("reviewerEmail")
    val reviewerEmail: String
)

internal fun ProductsResponseData.toDomain() = ProductsResponse(
    products = products.map { it.toDomain() },
    total = total,
    skip = skip,
    limit = limit
)

internal fun ProductResponseData.toDomain() = Product(
    id = id,
    title = title,
    description = description,
    category = category,
    price = price,
    discountPercentage = discountPercentage,
    rating = rating,
    stock = stock,
    tags = tags,
    brand = brand,
    sku = sku,
    weight = weight,
    dimensions = dimensions.toDomain(),
    warrantyInformation = warrantyInformation,
    shippingInformation = shippingInformation,
    availabilityStatus = availabilityStatus,
    reviews = reviews.map { it.toDomain() },
    returnPolicy = returnPolicy,
    minimumOrderQuantity = minimumOrderQuantity,
    meta = meta.toDomain(),
    images = images,
    thumbnail = thumbnail
)

internal fun DimensionsData.toDomain() = Dimensions(
    width = width,
    height = height,
    depth = depth
)

internal fun MetaData.toDomain() = Meta(
    createdAt = createdAt,
    updatedAt = updatedAt,
    barcode = barcode,
    qrCode = qrCode
)

internal fun ReviewData.toDomain() = Review(
    rating = rating,
    comment = comment,
    date = date,
    reviewerName = reviewerName,
    reviewerEmail = reviewerEmail
)