package dev.chirchir.domain.products.usecase

import dev.chirchir.domain.common.Response
import dev.chirchir.domain.common.usecase.UseCase
import dev.chirchir.domain.products.repository.ProductsRepository

class DeleteProductUseCase(
    private val productsRepository: ProductsRepository
): UseCase<Int, Unit> {
    override suspend fun execute(input: Int): Response<Unit> {
        return productsRepository.deleteProduct(input)
    }
}