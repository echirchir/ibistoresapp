package dev.chirchir.domain.products.usecase

import dev.chirchir.domain.common.Response
import dev.chirchir.domain.common.exception.InternetNotAvailableException
import dev.chirchir.domain.common.service.InternetService
import dev.chirchir.domain.common.usecase.UseCaseNoInput
import dev.chirchir.domain.products.model.ProductsResponse
import dev.chirchir.domain.products.repository.ProductsRepository

class GetProductsUseCase(
    private val internetService: InternetService,
    private val productsRepository: ProductsRepository
): UseCaseNoInput<ProductsResponse> {
    override suspend fun execute(): Response<ProductsResponse> {
        if (!internetService.isConnected()) {
            return Response.failure(InternetNotAvailableException)
        }
        return productsRepository.getProducts()
    }
}