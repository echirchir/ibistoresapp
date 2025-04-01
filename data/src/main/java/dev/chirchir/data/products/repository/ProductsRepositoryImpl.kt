package dev.chirchir.data.products.repository

import dev.chirchir.data.products.datasource.ProductsDataSource
import dev.chirchir.data.products.datasource.local.dao.ProductDao
import dev.chirchir.data.products.datasource.local.entity.toDomain
import dev.chirchir.data.products.datasource.local.entity.toEntity
import dev.chirchir.data.products.datasource.remote.model.toDomain
import dev.chirchir.domain.common.Response
import dev.chirchir.domain.products.model.Product
import dev.chirchir.domain.products.model.ProductsResponse
import dev.chirchir.domain.products.repository.ProductsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class ProductsRepositoryImpl(
    private val remoteDataSource: ProductsDataSource,
    private val productDao: ProductDao,
    private val ioDispatcher: CoroutineDispatcher
): ProductsRepository {
    override fun getProducts(limit: Int, skip: Int): Flow<Response<ProductsResponse>> = flow {

        try {
            val remoteProducts = remoteDataSource.getProducts(limit = limit, skip = skip).toDomain()
            val entities = remoteProducts.products.map { prod ->
               prod.toEntity()
            }

            productDao.syncProducts(entities)
            productDao.observeAllProducts()
                .map { productEntities -> productEntities.map { it.toDomain() } }
                .collect { cachedProducts ->
                    emit(Response.Success(
                        ProductsResponse(
                            products = cachedProducts,
                            total = remoteProducts.total,
                            skip = remoteProducts.skip,
                            limit = remoteProducts.limit
                        )
                    ))
                }
        } catch (e: Exception) {
            val cachedProducts = productDao.observeAllProducts()
                .first()
                .map { it.toDomain() }

            if (cachedProducts.isNotEmpty()) {
                emit(Response.Success(
                    ProductsResponse(
                        products = cachedProducts, skip = 0, limit = 0, total = cachedProducts.size
                    )
                ))
            } else {
                emit(Response.Failure(Exception("Failed to update product")))
            }
        }
    }.flowOn(ioDispatcher)

    override suspend fun getProductById(productId: Int): Response<Product> {
        return try {
            productDao.getProductById(productId)?.toDomain()?.let {
                Response.Success(it)
            } ?: Response.Failure(Exception("Product not found"))
        } catch (e: Exception) {
            Response.Failure(Exception("Failed to fetch product $productId"))
        }
    }

    override suspend fun updateProduct(product: Product): Response<Unit> {
        return try {
            productDao.updateProduct(product.toEntity())
            Response.Success(Unit)
        } catch (e: Exception) {
            Response.Failure(Exception("Failed to update product"))
        }
    }

    override suspend fun deleteProduct(productId: Int): Response<Unit> {
        return try {
            productDao.deleteProductById(productId)
            Response.Success(Unit)
        } catch (e: Exception) {
            Response.Failure (Exception("Failed to delete product"))
        }
    }

    override suspend fun toggleFavorite(productId: Int): Response<Unit> {
        return try {
            val product = productDao.getProductById(productId)
                ?: return Response.Failure(Exception("Product Not Found"))

            productDao.updateFavoriteStatus(productId, !product.favorited)
            Response.Success(Unit)
        } catch (e: Exception) {
            Response.Failure (Exception("Failed to toggle favorite"))
        }
    }

    override suspend fun syncWithRemote() {
        // TODO: implement feature 
    }

    override fun getFavorites(): Flow<Response<ProductsResponse>> = flow {
        try {
            productDao.observeFavoritedProducts()
                .collect { favorites ->
                    emit(
                        Response.Success(
                            ProductsResponse(
                                products = favorites.map { it.toDomain() },
                                total = favorites.size,
                                skip = 0,
                                limit = favorites.size
                            )
                        )
                    )
                }
        } catch (e: Exception) {
            emit(Response.Failure(Exception("Failed to load favorites: ${e.message}")))
        }
    }.flowOn(ioDispatcher)
}