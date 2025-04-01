package dev.chirchir.domain.products.usecase

import dev.chirchir.domain.common.Response
import dev.chirchir.domain.common.usecase.UseCaseFlow
import dev.chirchir.domain.products.model.PaginationModel
import dev.chirchir.domain.products.model.ProductsResponse
import dev.chirchir.domain.products.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow

class GetProductsUseCase(
    private val productsRepository: ProductsRepository
): UseCaseFlow<PaginationModel, ProductsResponse> {
    override fun execute(input: PaginationModel): Flow<Response<ProductsResponse>> {
        return productsRepository.getProducts(input.limit, input.skip)
    }
}