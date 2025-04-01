package dev.chirchir.domain.products.usecase

import dev.chirchir.domain.common.Response
import dev.chirchir.domain.common.usecase.UseCaseNoInputFlow
import dev.chirchir.domain.products.model.ProductsResponse
import dev.chirchir.domain.products.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow

class GetFavoritesUseCase(
    private val productsRepository: ProductsRepository
): UseCaseNoInputFlow<ProductsResponse> {
    override fun execute(): Flow<Response<ProductsResponse>> {
        return productsRepository.getFavorites()
    }
}